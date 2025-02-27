import os
import random
from datetime import datetime
import numpy as np
from datasets import Dataset
from transformers import AutoTokenizer, DataCollatorWithPadding, AutoModelForSequenceClassification, TrainingArguments, Trainer
import evaluate


class HermesTrainer:

    def __init__(self):
        self.tokenizer = AutoTokenizer.from_pretrained("distilbert-base-uncased")
        self.accuracy = evaluate.load("accuracy")

    @staticmethod
    def __split_dataset(dataset) -> (list, list):
        random.shuffle(dataset)
        train = dataset[:int((len(dataset) + 1) * .80)]  # Remaining 80% to training set
        test = dataset[int(len(dataset) * .80 + 1):]  # Splits 20% data to test set

        return train, test

    def __preprocess_function(self, examples):
        return self.tokenizer(examples["text"], truncation=True)

    def __compute_metrics(self, eval_pred):
        predictions, labels = eval_pred
        predictions = np.argmax(predictions, axis=1)
        return self.accuracy.compute(predictions=predictions, references=labels)

    def run(self, dataset, phone_number):
        dataset_train, dataset_test = self.__split_dataset(dataset)

        dataset_train = Dataset.from_list(dataset_train)
        dataset_test = Dataset.from_list(dataset_test)

        id2label = {0: "INTERESTING", 1: "NOT_INTERESTING"}
        label2id = {"INTERESTING": 0, "NOT_INTERESTING": 1}

        tokenized_dataset_train = dataset_train.map(self.__preprocess_function, batched=True)
        tokenized_dataset_test = dataset_test.map(self.__preprocess_function, batched=True)
        data_collator = DataCollatorWithPadding(tokenizer=self.tokenizer)
        model = AutoModelForSequenceClassification.from_pretrained(
            "/user_models/default",
            num_labels=2,
            id2label=id2label,
            label2id=label2id
        )

        output_dir = f"/user_models/{phone_number}/latest"

        if os.path.isdir(output_dir):
            os.rename(output_dir, f"/user_models/{phone_number}/{datetime.now().strftime('%Y_%m_%d_%H_%M_%S')}")

        training_args = TrainingArguments(
            output_dir=output_dir,
            learning_rate=2e-5,
            per_device_train_batch_size=16,
            per_device_eval_batch_size=16,
            num_train_epochs=5,
            weight_decay=0.01,
            logging_dir='train_logs',
        )

        trainer = Trainer(
            model=model,
            args=training_args,
            train_dataset=tokenized_dataset_train,
            eval_dataset=tokenized_dataset_test,
            tokenizer=self.tokenizer,
            data_collator=data_collator,
            compute_metrics=self.__compute_metrics,
        )

        trainer.train()

        trainer.save_model(output_dir)
