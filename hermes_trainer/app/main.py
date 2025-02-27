from hermes_mariadb.hermes_mariadb import HermesMariadb
from app.hermes_trainer import HermesTrainer


def finetune_user_models():
    hermes_trainer = HermesTrainer()

    for phone_number, feedback in HermesMariadb().get_feedback_group_by_user().items():
        hermes_trainer.run(feedback, phone_number)


if __name__ == '__main__':
    finetune_user_models()
