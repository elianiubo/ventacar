document.addEventListener("DOMContentLoaded", function () {
    /** @type {HTMLElement} Contenedor donde se muestran los resultados de vehículos */
    const contenedor = document.querySelector(".resultados");

    /** @type {URLSearchParams} Parámetros de la URL actual */
    const params = new URLSearchParams(window.location.search);
    const marca = params.get("marca");
    const tipo = params.get("tipo");
    const min = params.get("min");
    const max = params.get("max");

    /** @type {string} URL base para la búsqueda de vehículos */
    let url = "http://localhost:8080/api/vehiculos/buscar";
    /** @type {URLSearchParams} Parámetros de búsqueda */
    let query = new URLSearchParams();

    // Añade los filtros si están presentes
    if (marca) query.append("marca", marca);
    if (tipo) query.append("tipo", tipo);
    if (min) query.append("min", min);
    if (max) query.append("max", max);

    /** @type {string} URL completa con los filtros si los hay */
    const fullUrl = query.toString() ? `${url}?${query.toString()}` : url;

    // Obtener los datos de vehículos desde la API
    fetch(fullUrl)
        .then(res => res.json())
        .then(data => {
            /**
             * Vehículos reservados almacenados en localStorage.
             * Formato: { [vehiculoId: string]: timestampExpiracion: number }
             * @type {Object<string, number>}
             */
            const reservas = JSON.parse(localStorage.getItem("vehiculosReservados") || "{}");
            const ahora = Date.now();

            /**
             * Filtra vehículos eliminando los que están actualmente reservados.
             * @param {Object} vehiculo - Objeto del vehículo.
             * @returns {boolean} Verdadero si el vehículo no está reservado o la reserva ha expirado.
             */
            const resultadosFiltrados = data.filter(vehiculo => {
                const expira = reservas[vehiculo.id];
                return !expira || ahora > expira;
            });

            // Si no hay resultados disponibles
            if (resultadosFiltrados.length === 0) {
                contenedor.innerHTML = "<p>No se encontraron vehículos.</p>";
                return;
            }

            // Mostrar resultados de vehículos disponibles
            resultadosFiltrados.forEach(vehiculo => {
                /** @type {HTMLDivElement} */
                const div = document.createElement("div");
                div.classList.add("vehiculo");

                div.innerHTML = `
                    <img src="img/vehiculos/${vehiculo.imagen}" alt="${vehiculo.marca} ${vehiculo.tipo}" class="imagen-coche">
                    <h3>${vehiculo.marca} - ${vehiculo.tipo}</h3>
                    <p>Precio: ${new Intl.NumberFormat("es-ES", { style: "currency", currency: "EUR" }).format(vehiculo.precio)}</p>
                    <button class="boton-ver-coche" data-id="${vehiculo.id}">Ver Coche</button>
                `;

                // Evento para ver los detalles del vehículo
                div.querySelector(".boton-ver-coche").addEventListener("click", function () {
                    const id = this.getAttribute("data-id");
                    window.location.href = `detalle.html?id=${id}`;
                });

                contenedor.appendChild(div);
            });
        });
});
