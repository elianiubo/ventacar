document.addEventListener("DOMContentLoaded", function () {
    const contenedor = document.querySelector(".resultados");

    const params = new URLSearchParams(window.location.search);
    const marca = params.get("marca");
    const tipo = params.get("tipo");
    const min = params.get("min");
    const max = params.get("max");

    let url = "http://localhost:8080/api/vehiculos/buscar";
    let query = new URLSearchParams();

    if (marca) query.append("marca", marca);
    if (tipo) query.append("tipo", tipo);
    if (min) query.append("min", min);
    if (max) query.append("max", max);

    const fullUrl = query.toString() ? `${url}?${query.toString()}` : url;

    fetch(fullUrl)
        .then(res => res.json())
        .then(data => {
            const reservas = JSON.parse(localStorage.getItem("vehiculosReservados") || "{}");
            const ahora = Date.now();

            const resultadosFiltrados = data.filter(vehiculo => {
                const expira = reservas[vehiculo.id];
                return !expira || ahora > expira;
            });

            if (resultadosFiltrados.length === 0) {
                contenedor.innerHTML = "<p>No se encontraron vehículos.</p>";
                return;
            }

            resultadosFiltrados.forEach(vehiculo => {
                const div = document.createElement("div");
                div.classList.add("vehiculo");
                div.innerHTML = `
                    <img src="img/vehiculos/${vehiculo.imagen}" alt="${vehiculo.marca} ${vehiculo.tipo}" class="imagen-coche">
                    <h3>${vehiculo.marca} - ${vehiculo.tipo}</h3>
                    <p>Precio: ${new Intl.NumberFormat("es-ES", { style: "currency", currency: "EUR" }).format(vehiculo.precio)}</p>
                    <button class="boton-ver-coche" data-id="${vehiculo.id}">Ver Coche</button>
                `;
                div.querySelector(".boton-ver-coche").addEventListener("click", function () {
                    const id = this.getAttribute("data-id");
                    window.location.href = `detalle.html?id=${id}`;
                });

                contenedor.appendChild(div);
            });
        });
});
