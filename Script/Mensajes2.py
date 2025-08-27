import sys
import os
import urllib.parse
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException

# Validar argumentos
if len(sys.argv) < 3 or len(sys.argv[1:]) % 2 != 0:
    print("Uso: python enviar_mensaje.py <numero1> <mensaje1> <numero2> <mensaje2> ...")
    sys.exit(1)

# Obtener ruta del directorio actual del script
script_dir = os.path.dirname(os.path.abspath(__file__))

# Rutas relativas al script
chrome_driver_path = os.path.join(script_dir, "chromedriver.exe")

# Configurar navegador con perfil
options = webdriver.ChromeOptions()
options.add_argument("--user-data-dir=C:\\whatsapp_alerta\\perfil")
# Opciones adicionales para mejor estabilidad
options.add_argument("--no-sandbox")
options.add_argument("--disable-dev-shm-usage")
options.add_argument("--disable-blink-features=AutomationControlled")

# Iniciar driver
service = Service(chrome_driver_path)
driver = webdriver.Chrome(service=service, options=options)

# Configurar tiempo máximo de espera
wait = WebDriverWait(driver, 30)  # 30 segundos máximo

try:
    # Abrir WhatsApp Web
    driver.get("https://web.whatsapp.com")
    
    # Esperar a que WhatsApp Web cargue completamente
    # Buscar el elemento que indica que está cargado (por ejemplo, la barra de búsqueda o el panel de chats)
    print("Esperando que WhatsApp Web cargue...")
    
    try:
        # Intentar diferentes elementos que indican que WhatsApp está cargado
        wait.until(
            EC.any_of(
                EC.presence_of_element_located((By.XPATH, '//div[@contenteditable="true"][@data-tab="3"]')),  # Barra de búsqueda
                EC.presence_of_element_located((By.XPATH, '//div[contains(@class, "landing-wrapper")]')),      # Página de escaneo QR
                EC.presence_of_element_located((By.XPATH, '//div[@id="pane-side"]'))                          # Panel lateral de chats
            )
        )
        print("✅ WhatsApp Web cargado correctamente")
    except TimeoutException:
        print("⚠️ WhatsApp Web tardó mucho en cargar, continuando...")

    # Procesar cada número-mensaje
    args = sys.argv[1:]

    for i in range(0, len(args), 2):
        numero = args[i]
        mensaje = args[i + 1]
        mensaje_url = urllib.parse.quote(mensaje)

        print(f"Enviando mensaje a {numero}...")
        driver.get(f"https://web.whatsapp.com/send?phone={numero}&text={mensaje_url}")

        try:
            # Esperar a que aparezca el botón de enviar
            print("Esperando que aparezca el botón de enviar...")
            
            # Múltiples selectores para el botón de enviar (pueden cambiar según la versión)
            boton_enviar = wait.until(
                EC.any_of(
                    EC.element_to_be_clickable((By.XPATH, '//button[@aria-label="Enviar"]')),
                    EC.element_to_be_clickable((By.XPATH, '//button[@aria-label="Send"]')),
                    EC.element_to_be_clickable((By.XPATH, '//span[@data-icon="send"]')),
                    EC.element_to_be_clickable((By.XPATH, '//button[contains(@class, "send")]')),
                    EC.element_to_be_clickable((By.CSS_SELECTOR, '[data-icon="send"]'))
                )
            )
            
            # Hacer clic en el botón
            driver.execute_script("arguments[0].click();", boton_enviar)
            print(f"✅ Mensaje enviado a {numero}")
            
            # Esperar confirmación de envío (opcional)
            try:
                wait_short = WebDriverWait(driver, 5)
                wait_short.until(
                    EC.presence_of_element_located((By.XPATH, '//span[@data-icon="msg-check" or @data-icon="msg-dblcheck"]'))
                )
                print("✅ Confirmación de envío recibida")
            except TimeoutException:
                print("⚠️ No se recibió confirmación de envío, pero probablemente se envió")
                
        except TimeoutException:
            print(f"❌ Timeout: No se encontró el botón de enviar para {numero}")
            
            # Verificar si hay algún error específico
            try:
                # Buscar mensajes de error comunes
                error_elements = driver.find_elements(By.XPATH, '//*[contains(text(), "Número de teléfono no válido") or contains(text(), "Invalid phone number")]')
                if error_elements:
                    print(f"❌ Error: Número de teléfono no válido - {numero}")
                else:
                    print(f"❌ Error desconocido al enviar mensaje a {numero}")
            except:
                print(f"❌ No se pudo enviar el mensaje a {numero}")
                
        except Exception as e:
            print(f"❌ Error inesperado al enviar mensaje a {numero}: {e}")

        # Pequeña pausa entre mensajes para evitar ser detectado como spam
        try:
            WebDriverWait(driver, 2).until(lambda d: False)  # Pausa de 2 segundos
        except TimeoutException:
            pass  # Esta excepción es esperada

except Exception as e:
    print(f"❌ Error general: {e}")

finally:
    # Cerrar el navegador
    driver.quit()
    print("🔚 Navegador cerrado")