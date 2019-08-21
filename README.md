## Trabajo Práctico 2 - Simulacion de Sistemas
### Game of Life

#### Introducción
El juego de la vida es un autómata celular diseniado por John Conway. Desde un punto de vista teorico, todo lo que se puede computar algorítmicamente se puede computar en el juego de la vida.

#### Instrucciones de ejecución
1. Clonar el repositorio de github
2. Ejecutar la clase *Main* ubicada en *src/simulation/*

___Aclaración:___ En el archivo *configuration_file.txt* ubicado en *src/* se pueden cambiar diversos parámetros

#### Parámetros de configuración


| Parametros  | Descripción                   |
| ------------- | ------------------------------
| `length`      |    | 
| `final_step`   | La cantidad de veces que se aplicarán las reglas  | 
| `dimension`   |  Establece la dimensión (2 o 3) |
| `initialpattern`   | Configuracion inicial de celulas vivas y muertas |
| `rule`   |  Reglas a aplicar  |
| `particle_per_row`   |  Cantidad de particulas por fila |
| `particle_per_column`   |  Cantidad de particulas por columna |
| `particle_per_height`   |   Cantidad de particulas por altura |

#### Patrones
Valores que puede tomar el parametro _initialpattern_ :
###### 2D
+ initialpattern = random
+ initialpattern = battle
+ initialpattern = linear

###### 3D
+ initialpattern = random
+ initialpattern =

#### Reglas
Valores que puede tomar el parametro _rule_ :
###### 2D
+ rule = general_2d
+ rule =

###### 3D
+ rule = general_3d
+ rule = 


#### GRUPO 4 - Integrantes
- Della Sala, Rocio - 56507
- Giorgi, Maria Florencia - 56239
- Rodriguez, Ariel Andrés - 56030
