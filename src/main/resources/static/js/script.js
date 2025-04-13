document.addEventListener("DOMContentLoaded", function () {
    const btnBuscar = document.querySelector(".boton-busqueda button");
    const categoriaButtons = document.querySelectorAll(".boton__categoria");

    // Evento para buscar con los filtros
    btnBuscar.addEventListener("click", () => realizarBusqueda());

    // Evento para los 4 botones de tipo
    categoriaButtons.forEach(button => {
        button.addEventListener("click", function () {
            const tipo = button.querySelector("img").alt.toLowerCase();
            buscarPorCategoria(tipo);
        });
    });

    // Función para hacer la búsqueda con los filtros
    function realizarBusqueda() {
        const marca = document.querySelector("#marca").value;
        const tipo = document.querySelector("#tipo").value;
        const precio = document.querySelector("#precio").value;

        // Construcción de la url
        let url = "http://localhost:8080/api/vehiculos/buscar";
        let params = new URLSearchParams();

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

        let fullUrl = params.toString() ? `${url}?${params.toString()}` : url;
        console.log("Buscando en:", fullUrl);
        ejecutarBusqueda(fullUrl, ".resultados");
    }

    // Función para buscar coches por tipo (Los 4 botones)
    function buscarPorCategoria(tipo) {
        let url = `http://localhost:8080/api/vehiculos/buscar?tipo=${tipo}`;
        console.log("Buscando por categoría en:", url);
        ejecutarBusqueda(url, ".resultados-categorias");
    }

    // Función para ejecutar la petición y mostrar los resultados
    function ejecutarBusqueda(url, contenedorSelector) {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log("Resultados:", data);
                mostrarResultados(data, contenedorSelector);
            });
    }

    // Función para mostrar los resultados
    function mostrarResultados(vehiculos, contenedorSelector) {
        const resultadoDiv = document.querySelector(contenedorSelector);
        resultadoDiv.innerHTML = "";

        if (vehiculos.length === 0) {
            resultadoDiv.innerHTML = "<p>No se encontraron vehículos.</p>";
            return;
        }

        vehiculos.forEach(vehiculo => {
            const div = document.createElement("div");
            div.classList.add("vehiculo");
            div.innerHTML = `
                <h3>${vehiculo.marca} - ${vehiculo.tipo}</h3>
                <p>Precio: ${new Intl.NumberFormat("es-ES", { style: "currency", currency: "EUR" }).format(vehiculo.precio)}</p>
            `;
            resultadoDiv.appendChild(div);
        });
    }
});