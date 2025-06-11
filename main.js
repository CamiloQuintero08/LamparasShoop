// PRODUCTOS
const productos = [
    //Lamparas
    { 
        id: "lampara1",
        titulo: "Lámpara1",
        imagen: "./img/lamparas/01.jpg",
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 6500,       
    },
    { 
        id: "lampara2",
        titulo: "Lámpara2",
        imagen: "./img/lamparas/02.jpg",
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 2500,       
    },
    { 
        id: "lampara3",
        titulo: "Lámpara3",
        imagen: "./img/lamparas/03.jpg",
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 3500,       
    },
    { 
        id: "lampara4",
        titulo: "Lámpara4",
        imagen: "./img/lamparas/03.jpg",
        categoria: {
            nombre: "Lámparas",
            id: "lamparas"
        },
        precio: 3500,       
    },
    // Macetas
    { 
        id: "Maceta1",
        titulo: "Maceta1",
        imagen: "./img/macetas/maceta1.png",
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,       
    },
    { 
        id: "Maceta2",
        titulo: "Maceta2",
        imagen: "./img/macetas/maceta2.png",
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,       
    },
    { 
        id: "Maceta3",
        titulo: "Maceta3",
        imagen: "./img/macetas/maceta3.png",
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,       
    },
    { 
        id: "Maceta4",
        titulo: "Maceta4",
        imagen: "./img/macetas/maceta4.png",
        categoria: {
            nombre: "Macetas",
            id: "macetas"
        },
        precio: 3500,       
    },
    // Relojes
    { 
        id: "Reloj1",
        titulo: "Reloj1",
        imagen: "./img/relojes/reloj1.png",
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,       
    },
    { 
        id: "Reloj2",
        titulo: "Reloj2",
        imagen: "./img/relojes/reloj2.png",
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,       
    },
    { 
        id: "Reloj3",
        titulo: "Reloj3",
        imagen: "./img/relojes/reloj3.png",
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,       
    },
    { 
        id: "Reloj4",
        titulo: "Reloj4",
        imagen: "./img/relojes/reloj4.png",
        categoria: {
            nombre: "Relojes",
            id: "relojes"
        },
        precio: 3500,       
    },
    

]


const contenedorProductos = document.getElementById('contenedor-productos');
const botonesCategoterias = document.querySelectorAll('.boton-categoria');
const tituloPrincipal = document.querySelector("#titulo-principal");
let botonesAgregar = document.querySelectorAll('.producto-agregar');
const numerito = document.querySelector("#numerito");

function cargarProductos(productosElegidos) {

    contenedorProductos.innerHTML = ""; // Limpiar el contenedor de productos

    productosElegidos.forEach(producto => {

        const div = document.createElement('div');
        div.classList.add('producto');
        div.innerHTML = `
              <img class="producto-imagen" src="${producto.imagen}" alt="${producto.titulo}">
              <div class="producto-detalles">
                    <h3 class="producto-titulo">${producto.titulo}</h3>
                    <p class="producto-precio">$${producto.precio}</p>
                    <button class="producto-agregar" id="${producto.id}">Agregar</button>
              </div>
        `;	

        contenedorProductos.append(div);
        
    })

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