package Front.Panel;

import javax.swing.JMenuItem;

public abstract class Panel_extractos extends Panel{
    
    protected JMenuItem item_plantilla;
    protected JMenuItem item_exportar;
    protected JMenuItem item_exportar_todos;
    protected JMenuItem item_actualizar_todos;
    protected static final Runtime runtime = Runtime.getRuntime();
    protected static final String UBICACION_PS_CONVERTIRPDF = System.getProperty("user.dir") +"\\src\\Utilidades\\PDF\\ConvertirPdf.ps1";
    protected static final String UBICACION_PS_EXTRACTOS_MENSUALES = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Mensuales";
    protected static final String UBICACION_PS_EXTRACTOS_OCASIONALES = System.getProperty("user.home") + "\\Desktop\\Extractos\\Extractos Ocasionales";
    
    public Panel_extractos(String url){
        super(url);
    }

    @Override
    protected void config_pop_menu(){

        pop_menu = null;
        pop_menu = new CustomPopupMenu();

        item_actualizar = new JMenuItem("Modificar");
        item_adicionar = new JMenuItem("Adicionar");
        item_plantilla = new JMenuItem("Plantilla");
        item_exportar = new JMenuItem("Exportar");
        item_eliminar = new JMenuItem("Eliminar");
        item_exportar_todos = new JMenuItem("Exportar todos");
        item_actualizar_todos = new JMenuItem("Actualizar todos");

        pop_menu = new CustomPopupMenu();
        
        pop_menu.add(item_adicionar);
        pop_menu.add(item_actualizar);
        pop_menu.add(item_plantilla);
        pop_menu.add(item_exportar);
        pop_menu.add(item_eliminar);
        pop_menu.add(item_exportar_todos);
        pop_menu.add(item_actualizar_todos);

        config_listener_pop_menu();
    }

    
}
