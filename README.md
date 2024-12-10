# 📲 Map Room App

## 📝 Descripción

**Map Room App** es una aplicación diseñada para mostrar diversos puntos de interés en un mapa. Utiliza Room para gestionar los datos de los marcadores de forma persistente, asegurando una integración eficiente entre la interfaz y la lógica mediante un ViewModel.

<br>

## 📂 Detalles

- **Base de Datos:** Implementada con Room, con tres tablas relacionadas:

<br>

|markers| 	                
|---|
|id	(FK)|
|name	|
|latitude|	 
|longitude|	 
|description|	 
|markerTypeId (FK)|	 


<br>

|marker_types| 	
|---|
|id	|
|name|


<br>

 |favorites| 	
|---|
|markerId|

<br>
    
- **Datos:** Al menos 12 marcadores distribuidos en 4 tipos diferentes.
- **Gestión de Datos:** Uso de `ViewModel` para conectar Room con el mapa y asegurar que los datos se gestionen de forma reactiva.
- **Mapa:** Integración con la librería osmdroid en Compose para mostrar los marcadores y permitir desplegarlos para más información. 

<br>

## ⚙️ Funcionamiento

1. **Carga Inicial:**
   - La aplicación consulta la base de datos y recupera todos los marcadores existentes, mostrando sus coordenadas en el mapa.
   - Se utiliza un `ViewModel` para mantener la sincronización entre Room y el mapa.

2. **Gestión de Marcadores:**
   - Los usuarios pueden añadir un marcador a favoritos.
   - Los datos se almacenan en Room y se actualizan en tiempo real en el mapa.

3. **Interacción con el Mapa:**
   - Cada marcador tiene un icono diferenciado según su tipo.
   - Al hacer clic en un marcador, se muestra su título, el tipo de marcador, una descipción opcional, un botón para ampliar y otro para añadir a favoritos.
