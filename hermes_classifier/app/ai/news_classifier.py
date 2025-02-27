import torch
from transformers import pipeline, AutoTokenizer
from transformers.tokenization_utils_base import TruncationStrategy


class NewsClassifier:
    def __init__(self):
        self.__tokenizer = AutoTokenizer.from_pretrained(
            "bert-base-uncased",
            use_fast=True,
            low_cpu_mem_usage=False
        )
        self.__classifier = pipeline(
            "text-classification",
            model="dima806/news-category-classifier-distilbert",
            tokenizer=self.__tokenizer,
            device=0 if torch.cuda.is_available() else -1
        )

    def classify(self, text: str, _: str) -> dict:
        text = self.__tokenizer.decode(
            self.__tokenizer.encode(text, truncation=TruncationStrategy.ONLY_FIRST),
            skip_special_tokens=True,
            clean_up_tokenization_spaces=True
        )

        results = self.__classifier(text, top_k=50)
        mapped_result = {}

        for i in range(len(results)):
            mapped_result[results[i]['label']] = results[i]['score']

        return mapped_result
