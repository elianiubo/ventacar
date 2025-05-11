document.addEventListener("DOMContentLoaded", function () {
    /** @type {HTMLElement} Botón de búsqueda */
    const btnBuscar = document.querySelector(".boton-busqueda button");

    /** @type {NodeListOf<HTMLElement>} Lista de botones de categorías */
    const categoriaButtons = document.querySelectorAll(".boton__categoria");

    // Evento para buscar con los filtros al hacer clic en el botón de búsqueda
    btnBuscar.addEventListener("click", () => realizarBusqueda());

    // Evento para los botones de tipo de vehículo (categorías)
    categoriaButtons.forEach(button => {
        button.addEventListener("click", function () {
            /** @type {string} Tipo de vehículo basado en la imagen */
            const tipo = button.querySelector("img").alt.toLowerCase();
            buscarPorCategoria(tipo);
        });
    });

    /**
     * Realiza la búsqueda de vehículos con los filtros aplicados.
     * Toma los valores de los campos de filtro y construye la URL de búsqueda.
     * Luego redirige al usuario a la página de resultados.
     */
    function realizarBusqueda() {
        /** @type {string} Marca del vehículo */
        const marca = document.querySelector("#marca").value;
        /** @type {string} Tipo de vehículo */
        const tipo = document.querySelector("#tipo").value;
        /** @type {string} Rango de precios */
        const precio = document.querySelector("#precio").value;

        // Construcción de la URL de búsqueda
        let url = "http://localhost:8080/api/vehiculos/buscar";
        let params = new URLSearchParams();

        // Añadir los filtros a los parámetros de la URL
        if (marca) params.append("marca", marca);
        if (tipo) params.append("tipo", tipo);
        if (precio) {
            if (precio === "50000+") {
                params.append("min", "50000");
            } else {
                let [min, max] = precio.split("-");
                params.append("min", min);
                params.append("max", max);
            }
        }

        // Redirige a la página de resultados con los parámetros de búsqueda
        window.location.href = `resultados.html?${params.toString()}`;

        let fullUrl = params.toString() ? `${url}?${params.toString()}` : url;
        console.log("Buscando en:", fullUrl);
        ejecutarBusqueda(fullUrl, ".resultados");
    }

    /**
     * Realiza la búsqueda de vehículos por categoría (tipo de vehículo).
     * @param {string} tipo - El tipo de vehículo para filtrar la búsqueda (ejemplo: "sedan", "SUV").
     */
    function buscarPorCategoria(tipo) {
        let url = `http://localhost:8080/api/vehiculos/buscar?tipo=${tipo}`;
        console.log("Buscando por categoría en:", url);
        ejecutarBusqueda(url, ".resultados-categorias");
        window.location.href = `resultados.html?tipo=${tipo}`;
    }

    /**
     * Ejecuta la petición `fetch` para obtener los resultados de búsqueda y muestra los vehículos.
     * @param {string} url - La URL de la API para obtener los resultados de búsqueda.
     * @param {string} contenedorSelector - El selector del contenedor donde se mostrarán los resultados.
     */
    function ejecutarBusqueda(url, contenedorSelector) {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log("Resultados:", data);
                mostrarResultados(data, contenedorSelector);
            });
    }

    /**
     * Muestra los resultados de la búsqueda en el contenedor especificado.
     * @param {Array<Object>} vehiculos - Lista de vehículos obtenidos de la API.
     * @param {string} contenedorSelector - El selector del contenedor donde se mostrarán los resultados.
     */
    function mostrarResultados(vehiculos, contenedorSelector) {
        /** @type {HTMLElement} Contenedor donde se mostrarán los resultados */
        const resultadoDiv = document.querySelector(contenedorSelector);
        resultadoDiv.innerHTML = "";

        // Si no se encuentran vehículos, se muestra un mensaje
        if (vehiculos.length === 0) {
            resultadoDiv.innerHTML = "<p>No se encontraron vehículos.</p>";
            return;
        }

        // Mostrar cada vehículo encontrado
        vehiculos.forEach(vehiculo => {
            /** @type {HTMLDivElement} Elemento para cada vehículo */
            const div = document.createElement("div");
            div.classList.add("vehiculo");
            div.innerHTML = `
                <img src="img/vehiculos/${vehiculo.imagen}" alt="${vehiculo.marca} ${vehiculo.tipo}" class="imagen-coche">
                <h3>${vehiculo.marca} - ${vehiculo.tipo}</h3>
                <p>Precio: ${new Intl.NumberFormat("es-ES", { style: "currency", currency: "EUR" }).format(vehiculo.precio)}</p>
            `;
            resultadoDiv.appendChild(div);
        });
    }
});