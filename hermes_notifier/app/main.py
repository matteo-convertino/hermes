import concurrent.futures
import sys

from hermes_valkey.models.to_notify.message_to_notify_high import MessageToNotifyHigh
from hermes_valkey.models.to_notify.message_to_notify_low import MessageToNotifyLow

from app.hermes_notifier import HermesNotifier

if __name__ == '__main__':
    with concurrent.futures.ThreadPoolExecutor(max_workers=2) as executor:
        futures = [
            executor.submit(HermesNotifier(MessageToNotifyHigh).run),
            executor.submit(HermesNotifier(MessageToNotifyLow).run),
        ]

        concurrent.futures.wait(futures)

        # Should never stop
        sys.exit(1)
