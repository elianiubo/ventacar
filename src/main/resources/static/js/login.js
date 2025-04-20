document.addEventListener("DOMContentLoaded", function () {
	/*REGISTRO DEL USUARIO*/

	//VALORES del registro
	const nombre = document.getElementById("nombre");
	const pApellido = document.getElementById("pApellido");
	const sApellido = document.getElementById("sApellido");
	const emailRegistro = document.getElementById("email-registro");
	const contraseñaRegistro = document.getElementById("contraseña-registro");
	const confContraseña = document.getElementById("conf-contraseña");
	const telf = document.getElementById("telf");
	const cPostal = document.getElementById("c-postal");
	const bDay = document.getElementById("bDay");
	const bMonth = document.getElementById("bMonth");
	const bYear = document.getElementById("bYear");
	const formRegistro = document.getElementById("form-registro")
	const passRegex = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
	const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	//compara contraseñas
	function comparePassword(password1, password2, msg) {
		if (password1 !== password2) {

			return showError(confContraseña, msg, false); // Mostrar error en el campo de confirmar contraseña
		} else {
			return showSuccess(confContraseña); // Si las contraseñas coinciden, mostrar éxito
		}
	}

	//comprueba que el input tenga valor o no( cambiado por required en input)
	// function hasValue(input, message) {
	// 	if (input.value.trim() === "") {
	// 		return showError(input, message);
	// 	}
	// 	return showSuccess(input);
	// }

	function showError(input, message) {
		return showMessage(input, message, false);
	}

	function showSuccess(input) {
		return showMessage(input, "", true);
	}
	// muestra mensaje de error en <small>
	function showMessage(input, message, type) {
		const msg = input.parentNode.querySelector("small");
		if (msg) {
			msg.innerText = message;  // Asigna el mensaje de error
		}
		input.className = type ? "success" : "error";
		return type;
	}
	//valida los inputs del formato regex correo y contraseña
	function validate(input, valueRegex, invalidMsg) {
		const regexInput = valueRegex;

		const trimInput = input.value.trim();
		if (!regexInput.test(trimInput)) {
			return showError(input, invalidMsg);
		}
		return true;
	}
	//Validaciones de inputs y muestra del error
	const EMAIL_INVALID = "El formato del correo no es correcto";
	const PASSWORD_INVALID = " Debe contener al menos un número, una mayúscula, una minúscula y al menos 8 caracteres "
	const COMPARE_PASSWORD = "Las contraseñas no coinciden"

	formRegistro.addEventListener("submit", function (event) {
		event.preventDefault();
		const conReg = contraseñaRegistro.value;
		const confCon = confContraseña.value;
		// Llamadas a la validacion del form
		let emailValid = validate(emailRegistro, emailRegex, EMAIL_INVALID);
		let passValid = validate(contraseñaRegistro, passRegex, PASSWORD_INVALID)
		let compareValid = comparePassword(conReg, confCon, COMPARE_PASSWORD);

		// si form valido
		if (emailValid && passValid && compareValid) {

			/*FFETCH PARA GUARDAR LOS VALORES DEL REGISTRO A LA BD*/
			//guardo valores a objeto user
			const user = {
				email: emailRegistro.value,
				password: contraseñaRegistro.value,
				nombre: nombre.value,
				apellidos: `${pApellido.value} ${sApellido.value}`,
				telefono: telf.value,
				codigoPostal: cPostal.value,
				fechaNacimiento: `${bDay.value}-${bMonth.value}-${bYear.value}`
			};
			// Convertir el objeto en formato x-www-form-urlencoded
			const formBody = Object.keys(user)
				.map(key => encodeURIComponent(key) + '=' + encodeURIComponent(user[key]))
				.join('&');
			console.log(JSON.stringify(user, null, 2));

			fetch("http://localhost:8080/api/auth/signup", {
				method: "POST",
				headers: { "Content-Type": "application/x-www-form-urlencoded" },
				credentials: "include",
				body: formBody
			})
				.then(response => {
					if (!response.ok) {

						if (response.status === 400) {
							throw new Error("El correo ya está registrado");//controla el duplicado del correo
						} else {
							throw new Error("Error en el registro");
						}
					}
					return response.text();

				})
				.then(formBody => {
					alert("Estas registrado correctamente " + formBody.nombre)//de momento para saber que funciona
					window.location.href = "index.html"; // Redirige a la página principal (o venta coches)
				})
				.catch(error => {
					// muestra error de correo ya registrado
					const emailSmall = emailRegistro.parentNode.querySelector("small")
					emailSmall.innerText = error.message;
					emailSmall.style.color = "red";
					console.log('Error', error);
				})


		}


	});


	/*MANEJO DE UI ENTRE LOGIN  REGISTRO*/

	//manejo de los botones y cambio entre login y registro
	const btnRegistrarme = document.getElementById("btn-registrarme");
	const divRegistro = document.getElementById("registro-div");
	const divLogin = document.getElementById("login-div");
	//boton Cambia del login al registro
	btnRegistrarme.addEventListener("click", function (e) {
		console.log("Clicked")
		e.preventDefault();
		divRegistro.style.display = "block";
		divLogin.style.display = "none";
	})
	//boton cambia del registro al login
	const btnInicio = document.getElementById("btn-inicio");
	btnInicio.addEventListener("click", function (e) {
		e.preventDefault();
		divRegistro.style.display = "none";
		divLogin.style.display = "block";
	})

	/*COMPROBACION DE USUARIO REGISTRADO O NO EN EL LOGIN*/
	//manejo del login coje valores y comprueba si usuario ya registrado e inicia sesión
	const btnLogin = document.getElementById("btn-login")
	const formInicio = document.getElementById("form-inicio")
	const smallMessage = formInicio.querySelector("small");//element to show error
	btnLogin.addEventListener('click', function (e) {
		//coje valores y hace el fech de los valores en la BD
		const emailLogin = document.getElementById("email-inicio").value;
		const passwordLogin = document.getElementById("contraseña-inicio").value;

		const user = {
			email: emailLogin,
			password: passwordLogin
		};
		console.log(`${emailLogin} ${passwordLogin}`)


		fetch("http://localhost:8080/api/auth/login", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			credentials: "include",
			body: JSON.stringify(user)
		})
			.then(response => {
				if (!response.ok) {
					throw new Error("Error en la autenticación");
				}
				return response.text();
			})
			.then(data => {
				smallMessage.innerText = ""
				console.log(data); // Puedes ver "Login correcto" o lo que devuelva tu backend
				window.location.href = "index.html"; // Redirige a la página principal
				alert("Has iniciado sessión " + emailLogin)//de momento para saber
			})
			.catch(error => {
				smallMessage.innerText = "Usuario o contraseña incorrectos o no existen";
				smallMessage.style.color = "red";
				console.log('Error', error);
			})
	});

});
