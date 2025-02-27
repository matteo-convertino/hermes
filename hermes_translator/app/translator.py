from lingua import Language, LanguageDetectorBuilder
import argostranslate.package
import argostranslate.translate

from app.install_lang_package import AVAILABLE_TRANSLATIONS


class Translator:
    def __init__(self):
        self.detector = LanguageDetectorBuilder.from_languages(*Language.all()).build()

    def __detect_language(self, text: str) -> str | None:
        language = self.detector.detect_language_of(text)

        if language is None:
            return None

        return language.iso_code_639_1.name.lower()

    def translate(self, text: str) -> str:
        from_code = self.__detect_language(text)
        to_code = "en"

        if from_code is None:
            return ""
        elif from_code == to_code:
            return text
        elif from_code not in AVAILABLE_TRANSLATIONS:
            return ""

        return argostranslate.translate.translate(text, from_code, to_code)
