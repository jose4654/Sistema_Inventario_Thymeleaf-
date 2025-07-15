
## Instalación y ejecución

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/tu-repo.git
   cd Inventario
   ```

2. **Configurar la base de datos**
   - Edita `src/main/resources/application.properties` con los datos de tu base de datos.

3. **Compilar y ejecutar**
   ```bash
   ./mvnw spring-boot:run
   ```
   O usando Docker:
   ```bash
   docker build -t inventario-app .
   docker run -p 8080:8080 inventario-app
   ```

4. **Acceder a la aplicación**
   - Abre tu navegador en [http://localhost:8080](http://localhost:8080)

## Roadmap: Hacia un Ecommerce Completo

El objetivo es evolucionar este sistema hacia una plataforma de ecommerce. Las siguientes funcionalidades están planificadas:

- [ ] **Catálogo público de productos**: Página accesible para clientes donde puedan ver productos y categorías.
- [ ] **Carrito de compras**: Permitir a los usuarios agregar productos y gestionar su carrito.
- [ ] **Registro e inicio de sesión de clientes**: Separar usuarios administrativos de clientes finales.
- [ ] **Proceso de compra (checkout)**: Flujo para que los clientes realicen pedidos.
- [ ] **Integración de pagos en línea**: Conexión con pasarelas de pago (PayPal, Stripe, etc.).
- [ ] **Historial y seguimiento de pedidos para clientes**.
- [ ] **Mejoras en la interfaz de usuario**: Diseño responsivo y amigable para clientes y administradores.

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas colaborar, por favor abre un issue o un pull request.


---

**Autor:**  
José Grabiel
