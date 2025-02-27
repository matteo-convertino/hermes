from setuptools import setup, find_packages

setup(
    name='hermes_valkey',
    version='0.0.1',
    packages=find_packages(),
    install_requires=[
        "valkey==6.0.0b1"
    ],
    description='The Valkey library used in the Hermes project',
    author='Matteo Convertino',
    author_email='matteo@convertino.cloud',
    url='https://github.com/matteo-convertino',
    classifiers=[],
    python_requires='>=3.10',
)