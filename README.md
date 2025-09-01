## Inicio

Este proyecto se encarga de administar y automatizar diversos campos que pueden verse repetitivos para una empresa de transporte de servicio especial de pasajeros

## Observaciones

Por el momento, el proyecto se mantiene funcional, puede generar extractos junto a sus contratos, en caso de ser necesarios, automatizando todo el proceso para entregar dichos documentos en formato pdf.

Utiliza una base de datos ligera (SQLite), sin embargo, dicha base de datos se encuenta alojada en un  Drive, para que pueda fucionar de menera compartida con unos pocos usuarios, puesto que este tipo de base de datos, no esta realizada para soportar multiples usuarios como si lo puede hacer MySql.

## Requerimientos

- `LibreOffice`: Para la exportacion de los contratos, se requiere libre office, para que este realice la convercion de .docx (El formato en que se trabajan los formatos) a pdf.
- `Office`: El office de microsoft, en este caso se utiliza de la misma manera que libre office, sin embargo, es utilizado para la parte de los extractos, puesto que LibreOffice, no maneja adecuadamente la plantilla .xlsx omitiendo algunos fondos del documento, pricnipalmente es necesario para convertir de .xlsx a pdf.

- `Windows`: Para que el programa funcione correctamente, esta basado para funcionar de manera local utilizando algunas funcionalidades del sistema operativo Windows (Este sistema ha sido testeado de Windows 10 en adelante)
