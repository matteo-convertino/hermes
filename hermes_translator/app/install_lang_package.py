import argostranslate.package

AVAILABLE_TRANSLATIONS = ["it", "es", "de", "fr"]


def download_lang(from_code: str):
    to_code = "en"

    # Download and install Argos Translate package
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    package_to_install = next(
        filter(
            lambda x: x.from_code == from_code and x.to_code == to_code, available_packages
        )
    )

    argostranslate.package.install_from_path(package_to_install.download())


if __name__ == '__main__':
    for lang in AVAILABLE_TRANSLATIONS:
        download_lang(lang)
