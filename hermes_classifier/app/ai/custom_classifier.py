import os
import torch
from transformers import pipeline, AutoTokenizer
from transformers.tokenization_utils_base import TruncationStrategy


class CustomClassifier:
    def __init__(self):
        self.__tokenizer = AutoTokenizer.from_pretrained(
            "bert-base-uncased",
            use_fast=True,
            low_cpu_mem_usage=False
        )

    def classify(self, text: str, phone_number: str):
        user_model_path = f"/user_models/{phone_number}/latest"

        if not os.path.isdir(user_model_path) or len(os.listdir(user_model_path)) == 0:
            user_model_path = "/user_models/default"

        classify = pipeline(
            "text-classification",
            model=user_model_path,
            device=0 if torch.cuda.is_available() else -1,
        )

        text = self.__tokenizer.decode(
            self.__tokenizer.encode(text, truncation=TruncationStrategy.ONLY_FIRST),
            skip_special_tokens=True,
            clean_up_tokenization_spaces=True
        )

        result = classify(text)

        return {
            result[0]["label"]: result[0]["score"]
        }
