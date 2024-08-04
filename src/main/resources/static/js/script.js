
$(document).ready(function() {

	shownumbercart();
});

function back(){
	window.history.back();	
}

function shownumbercart(){
	let cart = getCart();
	
	
	let badgeNumberElement = document.getElementById('badge-number');
		let currentNumber = parseInt(cart.products.length);
		badgeNumberElement.textContent = currentNumber;
}

function getCart() {
    const cart = localStorage.getItem('shoppingCart');
    return cart ? JSON.parse(cart) : { products: []};
}


function addToCart(id,imagen,producto,descripcion,precio,stock) {
	var product = {
	    id: id,
		imagen: imagen,
	    producto: producto,
		descripcion: descripcion,
	    precio: precio,
		stock: stock
	};
	
    const cart = getCart();
	
	if (!Array.isArray(cart.products)) {
	        cart.products = [];
	    }
	
    const index = cart.products.findIndex(item => item.id === product.id);

    if (index !== -1) {
        // Si el producto ya está en el carrito, aumenta la cantidad
		if(cart.products[index].quantity < cart.products[index].stock){
			cart.products[index].quantity += 1;
		}
    } else {
		sumcartmenu();
        // Si el producto no está en el carrito, añádelo
        product.quantity = 1;	
        cart.products.push(product);
    }

    saveCart(cart);
}


function removeFromCart(productId) {
    let cart = getCart();
    cart.products = cart.products.filter(item => item.id !== productId);
	
    saveCart(cart);
	rescartmenu();
	showCart();
}

function saveCart(cart) {
    localStorage.setItem('shoppingCart', JSON.stringify(cart));
}


function showCart() {
	           const cart = getCart();
	           const cartContainer = $('#cart');
	           cartContainer.empty();
			   
			   const table = $(`
			   			                   <table class="table table-striped">
			   			                       <thead>
			   			                           <tr>
			   			                               <th>Producto</th>
													   <th></th>
			   									   <th>Stock</th>
			   			                               <th>Cantidad</th>
			   			                               <th>Precio</th>
			   			                               <th>Total</th>
			   			                               <th>Acciones</th>
			   			                           </tr>
			   			                       </thead>
			   			                       <tbody id="cart-body">
			   			                           <!-- Las filas de productos se agregarán aquí dinámicamente -->
			   			                       </tbody>
			   			                   </table>
			   			               `);
			   				cartContainer.append(table);			   
							const cartBody = $('#cart-body');
	           if (cart.products.length === 0) {
	               cartContainer.innerHTML = '<p>No hay productos en el carrito.</p>';
				   const productRow = `
				   				                    <tr>
				   				                        <td  colspan="7"  style="text-align: center;" >No hay productos en el carrito.</td>
				   				                    </tr>
				   				                `;
				   				                cartBody.append(productRow);
				   document.querySelectorAll('#subtotal').forEach(element => element.remove());
				   cartbuyhideshow();
	               return;
	           }
			   

				
	           cart.products.forEach(product => {
				let precio = parseFloat(product.precio).toFixed(2);
				const productRow = `
				                    <tr >
				                        <td>${product.producto}</td>
										<td class="cart-image"><img class="image" src=${product.imagen} ></td>
										<td>${product.stock}</td>
										<td><input class="btn-cart-plus" onclick=restProductCart(${product.id}) type="button" value="-"> <span>${product.quantity}</span>  <input class="btn-cart-plus" onclick=plusProductCart(${product.id}) type="button" value="+"></td>
				                        <td>S/ ${precio}</td>
				                        <td><span>S/ ${(product.quantity * product.precio).toFixed(2)}</span></td>
				                        <td>
				                            <button class="btn btn-danger" onclick="removeFromCart(${product.id})">Eliminar</button>
				                        </td>
				                    </tr>
				                `;
				                cartBody.append(productRow);
	           });
			   subtotal();
	       }

function restProductCart(id){
	let cart = getCart();
	
	const index = cart.products.findIndex(item => item.id === id);
	
	if (index !== -1) {
		if(cart.products[index].quantity != 0){
			cart.products[index].quantity -= 1;
			if(cart.products[index].quantity == 0){
				removeFromCart(id);
				showCart();
				return;
			}
		}
	}
	saveCart(cart);
	showCart();
}

function plusProductCart(id){
	let cart = getCart();
	const index = cart.products.findIndex(item => item.id === id);
	if (index !== -1) {
		if(cart.products[index].quantity < cart.products[index].stock){
			cart.products[index].quantity += 1;
		}
	}
	saveCart(cart);
	showCart();
}

function subtotal(){
	const cart = getCart();
	const subtotalContainer = document.getElementById('subtotal');
		
	if (cart.products.length === 0) {
		return;
	}
	
	let subtotal = 0;

	
	
	cart.products.forEach(product => {
		let quantity = parseInt(product.quantity);
		let precio = parseFloat(product.precio);
		subtotal += quantity * precio;
	});
	
	subtotal = subtotal.toFixed(2);

	subtotalContainer.innerHTML = '';
	const productElement = document.createElement('div');
	productElement.innerHTML = `
	<div class="">Subtotal: <span> ${subtotal} </span> </div>
	<div class="">TOTAL: <span>${subtotal}</span></div>
	<br>
	<div class="fw-bold"><span>Una vez confirmada la compra el vendedor contactará con usted.</span></div>
	`;
	subtotalContainer.appendChild(productElement);
			   
	
}

function sumcartmenu(){
	let badgeNumberElement = document.getElementById('badge-number');
	let currentNumber = parseInt(badgeNumberElement.textContent, 10);

	    badgeNumberElement.textContent = currentNumber + 1;
}

function rescartmenu(){
	let badgeNumberElement = document.getElementById('badge-number');
	let currentNumber = parseInt(badgeNumberElement.textContent, 10);

	    badgeNumberElement.textContent = currentNumber - 1;
}

function cartbuyhideshow(){
	let cart = getCart();
	if (cart.products.length === 0) {
		console.log("oculto");
		$('.cart-buy').hide();
		}	else {
			console.log("no oculto");
	        $('.cart-buy').show();
	    }
		
}
document.getElementById('togglePassword').addEventListener('click', function (e) {
	
	const passwordInput = document.getElementById('password');
	const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
	passwordInput.setAttribute('type', type);		  			 
	this.textContent = type === 'password' ? '👁️' : '🔒';

});	