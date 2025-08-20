# 💱 Conversor de Monedas - Challenge Alura
¡Bienvenido al Conversor de Monedas! Esta aplicación Java permite convertir entre más de 163 monedas utilizando tasas de cambio en tiempo real de la API [ExchangeRate-API](https://www.exchangerate-api.com).
## 🚀 Características
- ✅ Conversión en tiempo real entre 163+ monedas
- ✅ Interfaz de usuario intuitiva por consola
- ✅ Tasas de cambio actualizadas automáticamente
- ✅ Manejo robusto de errores y validación de entrada
- ✅ Código modular y bien estructurado
- ✅ Pruebas unitarias completas
## 📦 Tecnologías Utilizadas
- **Java 21**: Lenguaje de programación principal
- **Gson 2.13.1**: Librería para manipulación JSON
- **HTTP Client**: Cliente HTTP moderno de Java
- **ExchangeRate-API**: API para tasas de cambio en tiempo real
## 🏗️ Estructura del Proyecto
```
conversor-monedas/
├── src/
│   └── main/
│       └── java/
│           └── com/rodrigo/conversor/
│               ├── Main.java                 # Punto de entrada
│               ├── InterfazUsuario.java      # Interfaz de usuario
│               ├── ConversorMonedas.java     # Lógica de conversión
│               ├── FiltradorMonedas.java     # Filtrado de monedas
│               ├── RespuestaAPI.java         # POJO para JSON
│               ├── RespuestaHTTP.java        # Manejo de respuestas
│               ├── SolicitudHTTP.java        # Solicitudes HTTP
│               ├── ClienteHTTP.java          # Cliente base
│               └── APITest.java              # Pruebas iniciales
├── lib/
│   └── gson-2.13.1.jar          # Dependencia Gson
├── README.md                    # Este archivo
└── .gitignore                  # Archivos ignorados por Git
```
## ⚙️ Instalación y Ejecución
### Prerrequisitos
- Java JDK 21 o superior
- Conexión a internet (para obtener tasas actualizadas)
### Compilación
```bash
# Compilar todo el proyecto
javac -cp "src/main/java;lib/*" src/main/java/com/rodrigo/conversor/*.java
```
### Ejecución
```bash
# Ejecutar la aplicación
java -cp "src/main/java;lib/*" com.rodrigo.conversor.Main
```
## 🎯 Uso
1. Al iniciar la aplicación, se conectará automáticamente a la API para obtener las tasas más recientes
2. Verás un menú con opciones:
   - **1. Realizar conversión**: Convertir entre monedas
   - **2. Ver tasas disponibles**: Mostrar tasas principales
   - **3. Salir**: Finalizar la aplicación
3. Para realizar una conversión:
   - Ingresa la cantidad a convertir
   - Especifica la moneda de origen (ej: USD, EUR, ARS)
   - Especifica la moneda de destino (ej: JPY, GBP, BRL)
   - La aplicación mostrará el resultado formateado
## 📊 Ejemplo de Uso
```
🚀 Iniciando Conversor de Monedas...
📡 Conectando con API de tasas de cambio...
✅ Tasas obtenidas correctamente (163 monedas disponibles)
🎉 BIENVENIDO AL CONVERSOR DE MONEDAS
=====================================
📋 MENU PRINCIPAL
1. Realizar conversión
2. Ver tasas disponibles
3. Salir
Selecciona una opción: 1
💱 CONVERSIÓN DE MONEDAS
Ingresa la cantidad a convertir: 100
Moneda de origen (ej: USD, EUR, ARS): USD
Moneda de destino (ej: USD, EUR, ARS): EUR
💱 USD 100.00 = EUR 85.8098
💹 Tasa de cambio: 1 USD = 0.858098 EUR
```
## 🧪 Pruebas
El proyecto incluye pruebas exhaustivas para cada componente:
```bash
# Ejecutar pruebas específicas
java -cp "src/main/java;lib/*" com.rodrigo.conversor.TestRespuestaHTTP
java -cp "src/main/java;lib/*" com.rodrigo.conversor.TestConversor
```
## 🔧 Configuración
### Monedas Disponibles
La aplicación incluye soporte para todas las monedas principales, con especial énfasis en monedas latinoamericanas:
- USD (Dólar Estadounidense)
- EUR (Euro)
- GBP (Libra Esterlina)
- JPY (Yen Japonés)
- ARS (Peso Argentino)
- BRL (Real Brasileño)
- CLP (Peso Chileno)
- COP (Peso Colombiano)
- MXN (Peso Mexicano)
- BOB (Boliviano Boliviano)
### Personalización
Puedes modificar el archivo `FiltradorMonedas.java` para cambiar las monedas filtradas por defecto.
## 📝 Metodología de Desarrollo
Este proyecto fue desarrollado siguiendo las tarjetas del challenge de Alura:
1. **Tarjetas 1-4**: Configuración inicial del proyecto
2. **Tarjeta 5**: Implementación de solicitudes HTTP
3. **Tarjeta 6**: Manejo de respuestas HTTP
4. **Tarjeta 7**: Análisis JSON con Gson
5. **Tarjeta 8**: Filtrado de monedas específicas
6. **Tarjeta 9**: Lógica de conversión monetaria
7. **Tarjeta 10**: Interfaz de usuario textual
## 🤝 Contribución
Si deseas contribuir a este proyecto:
1. Haz fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request
## 📄 Licencia
Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
## 👨‍💻 Autor
**Rodrigo Mendoza** - [RodrigoM2025](https://github.com/RodrigoM2025)
Repositorio del proyecto: https://github.com/RodrigoM2025/conversor-monedas
---
¡Gracias por usar el Conversor de Monedas! 💙