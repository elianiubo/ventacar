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
            if (data.length === 0) {
                contenedor.innerHTML = "<p>No se encontraron vehículos.</p>";
                return;
            }

            data.forEach(vehiculo => {
                const div = document.createElement("div");
                div.classList.add("vehiculo");
                div.innerHTML = `
                <img src="img/vehiculos/${vehiculo.imagen}" alt="${vehiculo.marca} ${vehiculo.tipo}" class="imagen-coche">
                <h3>${vehiculo.marca} - ${vehiculo.tipo}</h3>
                <p>Precio: ${new Intl.NumberFormat("es-ES", { style: "currency", currency: "EUR" }).format(vehiculo.precio)}</p>
                <button class="boton-ver-coche">Ver Coche</button>
                `;

                contenedor.appendChild(div);
            });
        });
});