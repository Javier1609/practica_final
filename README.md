
Enlace al repositorio 
https://github.com/Javier1609/practica_final.git

Ejercicio comentado 


1. **Creación de la clase `Panel`**: Esta clase es un componente de la interfaz de usuario que contiene un `JComboBox` para seleccionar una acción y un `ActionListener` para manejar la acción seleccionada.

2. **Manejo de acciones en `Panel`**: Dependiendo de la acción seleccionada en el `JComboBox`, se ejecuta un bloque de código específico en el `ActionListener`. Las acciones que se pueden seleccionar son: "Agregar nuevo experimento", "Agregar población de bacterias", "Borrar población", "Ver información de la población" y "Abrir experimento".

3. **Agregar nuevo experimento**: Cuando se selecciona esta acción, se muestra un cuadro de diálogo para ingresar el nombre del nuevo experimento. Luego, se crea un nuevo experimento con el nombre ingresado y se guarda en un archivo.

4. **Agregar población de bacterias**: Cuando se selecciona esta acción, se abre una nueva ventana que permite al usuario ingresar la información de la nueva población de bacterias. Luego, se guarda esta información en un archivo.

5. **Borrar población**: Cuando se selecciona esta acción, se abre un `JFileChooser` que permite al usuario seleccionar un archivo. Luego, se lee el archivo y se muestra un cuadro de diálogo con las poblaciones disponibles para borrar. Cuando el usuario selecciona una población y hace clic en "Aceptar", se borra la población del archivo.

6. **Ver información de la población**: Cuando se selecciona esta acción, se abre un `JFileChooser` que permite al usuario seleccionar un archivo. Luego, se lee el archivo y se muestra un cuadro de diálogo con las poblaciones disponibles. Cuando el usuario selecciona una población y hace clic en "Aceptar", se muestra la información de la población.

7. **Abrir experimento**: Cuando se selecciona esta acción, se abre un `JFileChooser` que permite al usuario seleccionar un archivo. Luego, se lee el archivo y se muestra su contenido en una nueva ventana. El usuario puede editar el contenido del archivo en esta ventana y guardar los cambios haciendo clic en el botón "Guardar".
