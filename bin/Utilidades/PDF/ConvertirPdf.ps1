# Definiendo los parametros a utilizar
param(
    [string]$Parametro
)

$rutaCompleta = Join-Path -Path $Parametro -ChildPath "*.xlsx"

# Obtener la lista de archivos Excel en la carpeta
# $excelFiles = Get-ChildItem -Path "C:\Users\Juan Beltran\Desktop\Extractos_mensuales" -Filter *.xlsx
$excelFiles = Get-ChildItem -Path $rutaCompleta

# Crear un objeto de aplicación Excel
$excel = New-Object -ComObject Excel.Application

# Ocultar la aplicación Excel
$excel.Visible = $false

$excel.DisplayAlerts = $false # Desactivar los mensajes de advertencia
$excel.ScreenUpdating = $false # Desactivar la actualización de pantalla
# Recorrer cada archivo Excel y convertirlo a PDF
foreach ($file in $excelFiles) {
    $workbook = $excel.Workbooks.Open($file.FullName)
    $pdfPath = Join-Path -Path $file.Directory.FullName -ChildPath "$($file.BaseName).pdf"
    $workbook.ExportAsFixedFormat([Microsoft.Office.Interop.Excel.XlFixedFormatType]::xlTypePDF, $pdfPath)
    $workbook.Close()
    del $file
}

# Cerrar la aplicación Excel
$excel.Quit()

# Para ejecutar powershell -ExecutionPolicy ByPass -File "c:\Users\Juan Beltran\Desktop\ConvertirPdf.ps1"
