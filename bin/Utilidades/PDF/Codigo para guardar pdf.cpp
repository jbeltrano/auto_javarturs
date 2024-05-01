#include <iostream>
#include <stdlib.h>

using namespace std;

// int main(){

//     string script = "powershell -ExecutionPolicy ByPass -File \"c:\\Users\\Juan Beltran\\Desktop\\ConvertirPdf.ps1\"";

//     system(script.c_str());
//     return 0;
// }

int main(int argc, char *argv[]) {
    if (argc != 2) {
        cout << "Uso: " << argv[0] << " <ubicacion_del_archivo_ps1>" << endl;
        return 1;
    }

    string script = "powershell -ExecutionPolicy ByPass -File \"" + string(argv[1]) + "\"";
    system(script.c_str());

    return 0;
}
