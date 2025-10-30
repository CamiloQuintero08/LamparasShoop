const form = document.querySelector("#formProducto");
const mensaje = document.querySelector("#mensaje");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("nombre", document.querySelector("#nombre").value);
    formData.append("precio", document.querySelector("#precio").value);
    formData.append("stock", document.querySelector("#stock").value);
    formData.append("imagen", document.querySelector("#imagen").files[0]);

    try {
        const respuesta = await fetch("/productos/upload", {
            method: "POST",
            body: formData
        });

        if (respuesta.ok) {
            mensaje.innerHTML = `<div class="alert alert-success">✅ Producto guardado correctamente.</div>`;
            form.reset();
        } else {
            mensaje.innerHTML = `<div class="alert alert-danger">❌ Error al guardar el producto.</div>`;
        }
    } catch (error) {
        console.error("Error:", error);
        mensaje.innerHTML = `<div class="alert alert-danger">⚠️ Error de conexión con el servidor.</div>`;
    }
});
