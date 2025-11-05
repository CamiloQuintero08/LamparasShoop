// ===============================
// VARIABLES GLOBALES
// ===============================
let productos = [];
let productosFiltrados = [];

const contenedorProductos = document.querySelector("#contenedor-productos");
const tituloPrincipal = document.querySelector("#titulo-principal");

const inputBusqueda = document.querySelector("#busquedaNombre");
const selectCategoria = document.querySelector("#busquedaCategoria");
const selectOrden = document.querySelector("#ordenarProductos");
const inputPrecioMin = document.querySelector("#precioMin");
const inputPrecioMax = document.querySelector("#precioMax");
const btnFiltrar = document.querySelector("#btnFiltrar");
const btnLimpiar = document.querySelector("#btnLimpiar");
const botonesCategoterias = document.querySelectorAll('.boton-categoria');

let botonesAgregar = document.querySelectorAll(".agregar-carrito");
let numerito = document.querySelector("#numerito");

let productosEnCarrito = JSON.parse(localStorage.getItem("productos-en-carrito")) || [];

// ===============================
// CARGAR PRODUCTOS DESDE EL BACKEND
// ===============================
async function cargarProductos() {
    try {
        // ✅ Llamamos al endpoint JSON correcto
        const respuesta = await fetch("/productos/api");
        if (!respuesta.ok) throw new Error("Error al obtener productos del servidor");

        productos = await respuesta.json();
        productosFiltrados = productos;
        mostrarProductos(productos);
    } catch (error) {
        console.error("Error:", error);
        contenedorProductos.innerHTML = `<p>Error al cargar los productos.</p>`;
    }
}

// ===============================
// MOSTRAR PRODUCTOS EN EL HTML
// ===============================
function mostrarProductos(lista) {
    contenedorProductos.innerHTML = "";

    if (lista.length === 0) {
        contenedorProductos.innerHTML = `<p>No se encontraron productos.</p>`;
        return;
    }

    lista.forEach(prod => {
        const div = document.createElement("div");
        div.classList.add("producto");
        div.innerHTML = `
            <div class="producto-detalle">
                <img src="data:image/png;base64,${prod.imagen}" alt="${prod.nombre}" width="200px" height="200px">
                <h3>${prod.nombre}</h3>
                <p>$${prod.precio}</p>
                <button class="agregar-carrito" data-id="${prod.id}">
                    <i class="bi bi-cart-plus"></i> Agregar al carrito
                </button>
            </div>
        `;
        contenedorProductos.appendChild(div);
    });

    actualizarBotonesAgregar();
}

// ===============================
// BOTONES DE AGREGAR AL CARRITO
// ===============================
function actualizarBotonesAgregar() {
    botonesAgregar = document.querySelectorAll(".agregar-carrito");

    botonesAgregar.forEach(boton => {
        boton.addEventListener("click", agregarAlCarrito);
    });
}

function agregarAlCarrito(e) {
    const idProducto = e.currentTarget.dataset.id;
    const productoAgregado = productos.find(prod => prod.id == idProducto);

    if (!productoAgregado) return;

    const productoEnCarrito = productosEnCarrito.find(p => p.id == idProducto);

    if (productoEnCarrito) {
        productoEnCarrito.cantidad++;
    } else {
        productosEnCarrito.push({ ...productoAgregado, cantidad: 1 });
    }

    actualizarNumerito();
    localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
}

function actualizarNumerito() {
    const nuevoNumerito = productosEnCarrito.reduce((acc, prod) => acc + prod.cantidad, 0);
    numerito.innerText = nuevoNumerito;
}

// ===============================
// FILTROS Y BUSQUEDA
// ===============================
function filtrarProductos() {
    const nombre = inputBusqueda.value.toLowerCase();
    const categoria = selectCategoria.value;
    const precioMin = parseFloat(inputPrecioMin.value) || 0;
    const precioMax = parseFloat(inputPrecioMax.value) || Infinity;

    productosFiltrados = productos.filter(p => {
        const coincideNombre = p.nombre.toLowerCase().includes(nombre);
        const coincideCategoria = categoria === "todos" || p.categoria?.toLowerCase() === categoria;
        const coincidePrecio = p.precio >= precioMin && p.precio <= precioMax;
        return coincideNombre && coincideCategoria && coincidePrecio;
    });

    ordenarProductos();
    mostrarProductos(productosFiltrados);
}

function ordenarProductos() {
    const orden = selectOrden.value;

    switch (orden) {
        case "precioAsc":
            productosFiltrados.sort((a, b) => a.precio - b.precio);
            break;
        case "precioDesc":
            productosFiltrados.sort((a, b) => b.precio - a.precio);
            break;
        case "nombreAsc":
            productosFiltrados.sort((a, b) => a.nombre.localeCompare(b.nombre));
            break;
        case "nombreDesc":
            productosFiltrados.sort((a, b) => b.nombre.localeCompare(a.nombre));
            break;
        default:
            break;
    }
}

botonesCategoterias.forEach(boton => {
    boton.addEventListener('click', (e) => {
        botonesCategoterias.forEach(boton => boton.classList.remove('active'));
         e.currentTarget.classList.add('active');

            if (e.currentTarget.id !== "todos") {
                const productoCategoria = productos.find(producto => producto.categoria === e.currentTarget.id);
                tituloPrincipal.innerText = productoCategoria.categoria;

                const productosBoton = productos.filter(producto => producto.categoria === e.currentTarget.id);
                mostrarProductos(productosBoton);
            } else {
                tituloPrincipal.innerText = "Todos los productos";
                cargarProductos(productos);
            }
    })
})

function limpiarFiltros() {
    inputBusqueda.value = "";
    selectCategoria.value = "todos";
    selectOrden.value = "ninguno";
    inputPrecioMin.value = "";
    inputPrecioMax.value = "";
    productosFiltrados = productos;
    mostrarProductos(productos);
}

// ===============================
// EVENTOS
// ===============================
btnFiltrar.addEventListener("click", filtrarProductos);
btnLimpiar.addEventListener("click", limpiarFiltros);

// ===============================
// INICIO
// ===============================
document.addEventListener("DOMContentLoaded", () => {
    cargarProductos();
    actualizarNumerito();
});