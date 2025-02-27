import sys

from app.classifier.hermes_custom_classifier import HermesCustomClassifier
from classifier.hermes_news_classifier import HermesNewsClassifier
from classifier.hermes_zero_shot_classifier import HermesZeroShotClassifier

import concurrent.futures

if __name__ == '__main__':
    with concurrent.futures.ThreadPoolExecutor(max_workers=3) as executor:
        futures = [
            executor.submit(HermesZeroShotClassifier().run),
            executor.submit(HermesNewsClassifier().run),
            executor.submit(HermesCustomClassifier().run)
        ]

        concurrent.futures.wait(futures)

        # Should never stop
        sys.exit(1)
