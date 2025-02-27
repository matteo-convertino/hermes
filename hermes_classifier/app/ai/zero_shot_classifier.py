import torch
from transformers import pipeline

from app.utils.topics import TOPICS


class ZeroShotClassifier:
    def __init__(self):
        self.__classifier = pipeline(
            "zero-shot-classification",
            model="facebook/bart-large-mnli",
            batch_size=16,
            device=0 if torch.cuda.is_available() else -1,
        )

    def classify(self, text: str, _: str) -> dict:
        results = self.__classifier(text, TOPICS, multi_label=True)
        mapped_result = {}

        for i in range(len(TOPICS)):
            mapped_result[results['labels'][i]] = results['scores'][i]

        return mapped_result
