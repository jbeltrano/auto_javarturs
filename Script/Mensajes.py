import sys
import os
import time
import urllib.parse
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By

# Validar argumentos
if len(sys.argv) < 3 or len(sys.argv[1:]) % 2 != 0:
    print("Uso: python enviar_mensaje.py <numero1> <mensaje1> <numero2> <mensaje2> ...")
    sys.exit(1)

# Obtener ruta del directorio actual del script
script_dir = os.path.dirname(os.path.abspath(__file__))

# Rutas relativas al script
chrome_driver_path = os.path.join(script_dir, "chromedriver.exe")
# perfil_path = os.path.join(script_dir, "perfil")  # Carpeta de perfil portátil

# Configurar navegador con perfil
options = webdriver.ChromeOptions()
# options.add_argument(f"--user-data-dir={perfil_path}")
options.add_argument("--user-data-dir=C:\\whatsapp_alerta\\perfil")

# Iniciar driver
service = Service(chrome_driver_path)
driver = webdriver.Chrome(service=service, options=options)

# Abrir WhatsApp Web
driver.get("https://web.whatsapp.com")
time.sleep(10)

# Procesar cada número-mensaje
args = sys.argv[1:]

for i in range(0, len(args), 2):
    numero = args[i]
    mensaje = args[i + 1]
    mensaje_url = urllib.parse.quote(mensaje)

    driver.get(f"https://web.whatsapp.com/send?phone={numero}&text={mensaje_url}")
    time.sleep(10)

    try:

        boton = driver.find_element(By.XPATH, '//button[@aria-label="Enviar"]')
        boton.click()
        print(f"✅ Mensaje enviado a {numero}")
        
    except Exception as e:
        print(f"❌ No se pudo enviar el mensaje a {numero}:", e)

    time.sleep(5)

driver.quit()
