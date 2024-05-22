#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
    if (argc != 3) {
        cout << "Uso: " << argv[0] << " <ubicacion_del_archivo_ps1>" << endl;
        return 1;
    }

    string parametro = string(argv[2]);
    string script = "powershell -ExecutionPolicy ByPass -File \"" + string(argv[1]) + "\"" + " -parametro \""+ parametro + "\"";
    system(script.c_str());

    return 0;
}
