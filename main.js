// PRODUCTOS
const productos = [
    //Lamparas
    { 
        id: "lampara1",
        titulo: "Lámpara1",
        imagenes: ["./img/lamparas/01.jpg", "./img/lamparas/02.jpg", "./img/lamparas/03.jpg"],
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 6500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "lampara2",
        titulo: "Lámpara2",
        imagenes: ["./img/lamparas/01.jpg", "./img/lamparas/02.jpg", "./img/lamparas/03.jpg"],
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 2500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "lampara3",
        titulo: "Lámpara3",
        imagenes: ["./img/lamparas/01.jpg", "./img/lamparas/02.jpg", "./img/lamparas/03.jpg"],
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "lampara4",
        titulo: "Lámpara4",
        imagenes: ["./img/lamparas/01.jpg", "./img/lamparas/02.jpg", "./img/lamparas/03.jpg"],
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    // Macetas
    { 
        id: "Maceta1",
        titulo: "Maceta1",
        imagenes: ["./img/macetas/maceta1.png", "./img/macetas/maceta2.png", "./img/macetas/maceta3.png"],
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Maceta2",
        titulo: "Maceta2",
        imagenes: ["./img/macetas/maceta1.png", "./img/macetas/maceta2.png", "./img/macetas/maceta3.png"],
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Maceta3",
        titulo: "Maceta3",
        imagenes: ["./img/macetas/maceta1.png", "./img/macetas/maceta2.png", "./img/macetas/maceta3.png"],
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Maceta4",
        titulo: "Maceta4",
        imagenes: ["./img/macetas/maceta1.png", "./img/macetas/maceta2.png", "./img/macetas/maceta3.png"],
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    // Relojes
    { 
        id: "Reloj1",
        titulo: "Reloj1",
        imagenes: ["./img/relojes/reloj1.png", "./img/relojes/reloj2.png", "./img/relojes/reloj3.png"],
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Reloj2",
        titulo: "Reloj2",
        imagenes: ["./img/relojes/reloj1.png", "./img/relojes/reloj2.png", "./img/relojes/reloj3.png"],
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Reloj3",
        titulo: "Reloj3",
        imagenes: ["./img/relojes/reloj1.png", "./img/relojes/reloj2.png", "./img/relojes/reloj3.png"],
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    { 
        id: "Reloj4",
        titulo: "Reloj4",
        imagenes: ["./img/relojes/reloj1.png", "./img/relojes/reloj2.png", "./img/relojes/reloj3.png"],
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,
        informacion: "Lorem ipsum :V"       
    },
    

]


const contenedorProductos = document.getElementById('contenedor-productos');
const botonesCategoterias = document.querySelectorAll('.boton-categoria');
const tituloPrincipal = document.querySelector("#titulo-principal");
let botonesAgregar = document.querySelectorAll('.producto-agregar');
const numerito = document.querySelector("#numerito");
const infoOverlay = document.querySelector(".overlay-info-ventana");
const infoVentana = document.querySelector(".info-ventana");
const busquedaNombre = document.getElementById("busquedaNombre");
const busquedaCategoria = document.getElementById("busquedaCategoria");
const precioMin = document.getElementById("precioMin");
const precioMax = document.getElementById("precioMax");
const btnFiltrar = document.getElementById("btnFiltrar");
const btnLimpiar = document.getElementById("btnLimpiar");
const ordenarProductos = document.getElementById("ordenarProductos");

function cargarProductos(productosElegidos) {

    contenedorProductos.innerHTML = ""; // Limpiar el contenedor de productos   

    productosElegidos.forEach(producto => {

        const div = document.createElement('div');
        div.classList.add('producto');
        div.innerHTML = `
            <div class="contenedor-imagen">
              <img class="producto-imagen" src="${producto.imagenes[0]}" alt="${producto.titulo}">
              <img class="producto-imagen" src="${producto.imagenes[1]}" alt="${producto.titulo}">
              <img class="producto-imagen" src="${producto.imagenes[2]}" alt="${producto.titulo}">
              <button type="button" class="boton-info" id="mostrar-info-${producto.id}"><i class="bi bi-info-circle-fill"></i></button>
            </div>
              <div class="producto-detalles">
                    <h3 class="producto-titulo">${producto.titulo}</h3>
                    <p class="producto-precio">$${producto.precio}</p>
                    <button class="producto-agregar" id="${producto.id}">Agregar</button>
              </div>
        `;	

        contenedorProductos.append(div);
        
    })

    const infoBotones = document.querySelectorAll(".boton-info");
    infoBotones.forEach(boton => {
        boton.addEventListener("click", (e) => {
            infoOverlay.style.display = "flex";
            const idProducto = e.currentTarget.id.split("-")[2];
            const productoSeleccionado = productos.find(producto => producto.id == idProducto);
            infoVentana.innerHTML = `
                <div style="display: flex; width: 100%; justify-content: space-between; ">
                    <h2 >${productoSeleccionado.titulo}</h2>
                    <button type="button" onClick=cerrarInfo()>X</button>
                </div>
                <div>
                    ${productoSeleccionado.informacion}
                </div>
            `;
        });
    });

    actualizarBotonesAgregar();
   
}

cargarProductos(productos);

botonesCategoterias.forEach(boton => {
    boton.addEventListener('click', (e) => {
        botonesCategoterias.forEach(boton => boton.classList.remove('active'));
         e.currentTarget.classList.add('active');

            if (e.currentTarget.id !== "todos") {
                const productoCategoria = productos.find(producto => producto.categoria.id === e.currentTarget.id);
                tituloPrincipal.innerText = productoCategoria.categoria.nombre;

                const productosBoton = productos.filter(producto => producto.categoria.id === e.currentTarget.id);
                cargarProductos(productosBoton);
            } else {
                tituloPrincipal.innerText = "Todos los productos";
                cargarProductos(productos);
            }
    })
})


function actualizarBotonesAgregar() {
    botonesAgregar = document.querySelectorAll('.producto-agregar');

    botonesAgregar.forEach(boton => {
        boton.addEventListener('click', agregarAlCarrito);
    })

}

let productosEnCarrito;

let productosEnCarritoLS = localStorage.getItem("productos-en-carrito");

if (productosEnCarritoLS) {
    productosEnCarrito = JSON.parse(productosEnCarritoLS); 
    actualizarNumerito();
} else {
    productosEnCarrito = [];
}

function agregarAlCarrito(e) {
  
    const idBoton = e.currentTarget.id;
    const productosAgregado = productos.find(producto => producto.id === idBoton);

    if (productosEnCarrito.some(producto => producto.id === idBoton)) {
        const index = productosEnCarrito.findIndex(producto => producto.id === idBoton);
        productosEnCarrito[index].cantidad++;

    } else {
        productosAgregado.cantidad = 1;
        productosEnCarrito.push(productosAgregado);
    } 
    
    actualizarNumerito();

    localStorage.setItem("productos-en-carrito", JSON.stringify(productosEnCarrito));
}

function actualizarNumerito() {
    let nuevoNumerito = productosEnCarrito.reduce((acc, producto) => acc + producto.cantidad, 0);
    numerito.innerText = nuevoNumerito;
}

function cerrarInfo() {
        infoOverlay.style.display = "none";
}

infoOverlay.addEventListener("click", cerrarInfo)

function filtrarProductos() {
    let nombre = busquedaNombre.value.toLowerCase();
    let categoria = busquedaCategoria.value;
    let min = precioMin.value ? parseFloat(precioMin.value) : 0;
    let max = precioMax.value ? parseFloat(precioMax.value) : Infinity;

    let filtrados = productos.filter(p => {
        let coincideNombre = p.titulo.toLowerCase().includes(nombre);
        let coincideCategoria = categoria === "todos" || p.categoria.id === categoria;
        let coincidePrecio = p.precio >= min && p.precio <= max;
        return coincideNombre && coincideCategoria && coincidePrecio;
    });

    // Ordenamiento
    if (ordenarProductos.value === "precioAsc") {
        filtrados.sort((a, b) => a.precio - b.precio);
    } else if (ordenarProductos.value === "precioDesc") {
        filtrados.sort((a, b) => b.precio - a.precio);
    } else if (ordenarProductos.value === "nombreAsc") {
        filtrados.sort((a, b) => a.titulo.localeCompare(b.titulo));
    } else if (ordenarProductos.value === "nombreDesc") {
        filtrados.sort((a, b) => b.titulo.localeCompare(a.titulo));
    }

    if (categoria !== "todos") {
        const productoCategoria = productos.find(producto => producto.categoria.id === categoria);
        if (productoCategoria) {
            tituloPrincipal.innerText = productoCategoria.categoria.nombre;
        }
    } else {
        tituloPrincipal.innerText = "Todos los productos";
    }

    cargarProductos(filtrados);
}

// Ejecutar automáticamente al cambiar orden
ordenarProductos.addEventListener("change", filtrarProductos);


// Función para limpiar los filtros
function limpiarFiltros() {
    busquedaNombre.value = "";
    busquedaCategoria.value = "todos";
    precioMin.value = "";
    precioMax.value = "";
    tituloPrincipal.innerText = "Todos los productos";
    cargarProductos(productos);
}

// Eventos de los botones del filtro
if (btnFiltrar && btnLimpiar) {
    btnFiltrar.addEventListener("click", filtrarProductos);
    btnLimpiar.addEventListener("click", limpiarFiltros);
}


