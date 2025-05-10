document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        document.getElementById("marca__vehiculo").textContent = "Vehículo no encontrado";
        return;
    }

    fetch(`http://localhost:8080/api/vehiculos/${id}`)
        .then(response => {
            if (!response.ok) throw new Error("Vehículo no encontrado");
            return response.json();
        })
        .then(vehiculo => {
            document.getElementById("imagen__vehiculo").src = `img/vehiculos/${vehiculo.imagen}`;
            document.getElementById("imagen__vehiculo").alt = `${vehiculo.marca} ${vehiculo.tipo}`;
            document.getElementById("marca__vehiculo").textContent = vehiculo.marca;
            document.getElementById("modelo__vehiculo").textContent = vehiculo.tipo;
            document.getElementById("descripcion__vehiculo").textContent = vehiculo.descripcion || "Sin descripción.";
        })
        .catch(error => {
            console.error(error);
            document.querySelector(".descripcion").innerHTML = "<p>Error al cargar el vehículo.</p>";
        });
});
//Funcionn para reservar el vehiculo
function reservarVehiculo(id) {
    const reservas = JSON.parse(localStorage.getItem("vehiculosReservados") || "{}");
    const ahora = Date.now();
    reservas[id] = ahora + 60000; 
    localStorage.setItem("vehiculosReservados", JSON.stringify(reservas));
}

function accion(tipo) {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (tipo === 'reservar') {
        reservarVehiculo(id);
        const main = document.querySelector("main");
        main.innerHTML = `
            <div class="contenedor" style="text-align: center; padding: 8rem 2rem;">
                <h1 style="font-size: 2.8rem; color: var(--secondary);">
                    Muchas gracias por confiar en Ventacar
                </h1>
                <p style="font-size: 2rem; margin-top: 2rem;">
                    El vehículo se ha reservado correctamente.
                </p>
            </div>
        `;
        window.scrollTo({ top: 0, behavior: "smooth" });
    } else if (tipo === 'comprar') {
        const main = document.querySelector("main");
        main.innerHTML = `
            <div class="contenedor" style="max-width: 60rem; margin: 8rem auto; text-align: center;">
                <h2 style="font-size: 2.4rem; color: var(--secondary);">Finalizar compra</h2>
                <form id="form-factura" style="margin-top: 3rem;">
                    <label for="direccion" style="font-size: 1.8rem; display: block; margin-bottom: 1rem;">
                        Dirección de facturación:
                    </label>
                    <input type="text" id="direccion" name="direccion" required
                        style="width: 100%; padding: 1rem; font-size: 1.6rem; border-radius: 0.5rem; border: 1px solid #ccc;">
                    <button id="btn-generar-factura" type="submit" class="boton" style="margin-top: 2rem;">
                        Generar factura
                    </button>
                </form>
            </div>
        `;
        window.scrollTo({ top: 0, behavior: "smooth" });
        setTimeout(() => {
            const form = document.getElementById("form-factura");
            form.addEventListener("submit", (e) => {
                e.preventDefault();
                const direccion = document.getElementById("direccion").value.trim();
                if (direccion) {
                    alert(`Factura generada para:\n${direccion}`);
                }
            });
        }, 0);
    }
}