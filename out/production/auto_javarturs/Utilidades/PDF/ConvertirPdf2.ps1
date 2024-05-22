param(
    [string]$Parametro
)

$rutaCompleta = Join-Path -Path $Parametro -ChildPath "*.docx"

# Obtener la lista de archivos Word en la carpeta
$wordFiles = Get-ChildItem -Path $rutaCompleta

# Crear un objeto de aplicación Word
$word = New-Object -ComObject Word.Application

# Ocultar la aplicación Word
$word.Visible = $false

# Recorrer cada archivo Word y convertirlo a PDF
foreach ($file in $wordFiles) {
    $document = $word.Documents.Open($file.FullName)
    $pdfPath = Join-Path -Path $file.Directory.FullName -ChildPath "$($file.BaseName).pdf"
    $document.SaveAs([ref] $pdfPath, [ref] 17) # 17 es el formato para PDF
    $document.Close()
    #Remove-Item $file.FullName
}

# Cerrar la aplicación Word
$word.Quit()

# Para ejecutar: powershell -ExecutionPolicy ByPass -File "c:\ruta\a\tu\script.ps1"
