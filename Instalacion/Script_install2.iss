[Setup]
; Información básica del instalador
AppId={{12345678-1234-1234-1234-123456789012}
AppName=JavaRturs
AppVersion=1.0
AppVerName=JavaRturs 1.0
AppPublisher=Tu Empresa
AppPublisherURL=http://www.tuempresa.com/
AppSupportURL=http://www.tuempresa.com/support/
AppUpdatesURL=http://www.tuempresa.com/updates/
DefaultDirName={autopf}\JavaRturs
DefaultGroupName=JavaRturs
AllowNoIcons=yes
OutputDir=output
OutputBaseFilename=JavaRturs_Setup
SetupIconFile=LOGO_VF.ico
Compression=lzma
SolidCompression=yes
WizardStyle=modern
PrivilegesRequired=admin

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; Archivo principal JAR
Source: "auto_javarturs.jar"; DestDir: "{app}"; Flags: ignoreversion

; Carpeta src con todos sus contenidos
Source: "src\*"; DestDir: "{app}\src"; Flags: ignoreversion recursesubdirs createallsubdirs

; Carpeta JDK con todos sus contenidos
Source: "jdk\*"; DestDir: "{app}\jdk"; Flags: ignoreversion recursesubdirs createallsubdirs

; Icono de la aplicación
Source: "LOGO_VF.ico"; DestDir: "{app}"; Flags: ignoreversion

; Launcher VBS (ejecutable principal)
Source: "launcher.vbs"; DestDir: "{app}"; Flags: ignoreversion

; Instalador de LibreOffice
Source: "LibreOffice_installer.msi"; DestDir: "{tmp}"; Flags: deleteafterinstall

[Icons]
Name: "{group}\JavaRturs"; Filename: "{app}\launcher.vbs"; IconFilename: "{app}\LOGO_VF.ico"
Name: "{group}\{cm:UninstallProgram,JavaRturs}"; Filename: "{uninstallexe}"
Name: "{autodesktop}\JavaRturs"; Filename: "{app}\launcher.vbs"; IconFilename: "{app}\LOGO_VF.ico"; Tasks: desktopicon

[Run]
; Instalar LibreOffice ANTES de completar la instalación principal
Filename: "msiexec.exe"; Parameters: "/i ""{tmp}\LibreOffice_installer.msi"" /passive"; StatusMsg: "Instalando LibreOffice (requerido)..."; Flags: waituntilterminated; Check: not IsLibreOfficeInstalled

[Code]
// Declaración de funciones de la API de Windows
function PostMessage(hWnd: HWND; Msg: UINT; wParam, lParam: Longint): BOOL;
external 'PostMessageA@user32.dll stdcall';

function GetLibreOfficePath: string;
begin
  Result := '';
  
  // Buscar la ruta de instalación de LibreOffice
  if RegQueryStringValue(HKLM, 'SOFTWARE\LibreOffice\LibreOffice', 'Path', Result) then
  begin
    Result := Result + '\program';
    Exit;
  end;
  
  // Verificar en el registro de 64 bits (en sistemas de 64 bits)
  if IsWin64 and RegQueryStringValue(HKLM, 'SOFTWARE\WOW6432Node\LibreOffice\LibreOffice', 'Path', Result) then
  begin
    Result := Result + '\program';
    Exit;
  end;
  
  // Verificar rutas comunes de instalación
  if DirExists('C:\Program Files\LibreOffice\program') then
  begin
    Result := 'C:\Program Files\LibreOffice\program';
    Exit;
  end;
  
  if DirExists('C:\Program Files (x86)\LibreOffice\program') then
  begin
    Result := 'C:\Program Files (x86)\LibreOffice\program';
    Exit;
  end;
end;

function IsLibreOfficeInstalled: Boolean;
var
  Version: string;
begin
  // Verificar si LibreOffice ya está instalado
  Result := False;
  
  // Verificar en el registro de 32 bits
  if RegQueryStringValue(HKLM, 'SOFTWARE\LibreOffice\LibreOffice', 'Path', Version) then
  begin
    Result := True;
    Exit;
  end;
  
  // Verificar en el registro de 64 bits (en sistemas de 64 bits)
  if IsWin64 and RegQueryStringValue(HKLM, 'SOFTWARE\WOW6432Node\LibreOffice\LibreOffice', 'Path', Version) then
  begin
    Result := True;
    Exit;
  end;
  
  // Verificar en rutas comunes de instalación
  if DirExists('C:\Program Files\LibreOffice') or 
     DirExists('C:\Program Files (x86)\LibreOffice') then
  begin
    Result := True;
    Exit;
  end;
end;

function InitializeSetup(): Boolean;
begin
  Result := True;
  
  // Verificar si LibreOffice está instalado al inicio
  if IsLibreOfficeInstalled then
  begin
    MsgBox('LibreOffice ya está instalado en el sistema.', mbInformation, MB_OK);
  end
  else
  begin
    if MsgBox('JavaRturs requiere LibreOffice para funcionar correctamente.' + #13#10 + 
              'LibreOffice será instalado automáticamente durante este proceso.' + #13#10#13#10 + 
              '¿Desea continuar con la instalación?', mbConfirmation, MB_YESNO) = IDNO then
    begin
      Result := False;
    end;
  end;
end;

procedure CurStepChanged(CurStep: TSetupStep);
var
  LibreOfficePath: string;
  CurrentPath: string;
begin
  if CurStep = ssPostInstall then
  begin
    // Verificar que LibreOffice se instaló correctamente
    if not IsLibreOfficeInstalled then
    begin
      MsgBox('ADVERTENCIA: No se pudo verificar la instalación de LibreOffice.' + #13#10 + 
             'JavaRturs podría no funcionar correctamente sin LibreOffice.' + #13#10#13#10 +
             'Por favor, instale LibreOffice manualmente si es necesario.', 
             mbError, MB_OK);
    end
    else
    begin
      // Agregar LibreOffice a las variables de entorno PATH
      LibreOfficePath := GetLibreOfficePath;
      if LibreOfficePath <> '' then
      begin
        if RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', CurrentPath) then
        begin
          // Verificar si LibreOffice ya está en el PATH
          if Pos(UpperCase(LibreOfficePath), UpperCase(CurrentPath)) = 0 then
          begin
            // Agregar LibreOffice al PATH del sistema
            if CurrentPath <> '' then
              CurrentPath := CurrentPath + ';' + LibreOfficePath
            else
              CurrentPath := LibreOfficePath;
            
            if RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', CurrentPath) then
            begin
              // Notificar al sistema sobre el cambio en las variables de entorno
              // Enviar mensaje WM_SETTINGCHANGE para que las aplicaciones se enteren del cambio
              PostMessage(HWND_BROADCAST, $001A, 0, 0);
            end;
          end;
        end;
      end;
    end;
  end;
end;

[UninstallDelete]
Type: filesandordirs; Name: "{app}"

[Messages]
spanish.BeveledLabel=JavaRturs Setup
english.BeveledLabel=JavaRturs Setup