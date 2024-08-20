$(document).ready(function() {

	$("#btnactcli").prop("disabled", true);

	$("#checkclient").on("change", function() {
		if ($(this).is(":checked")) {

			$(".form-control").each(function() {
				$(this).removeClass("form-control").addClass("input-group-text");
			});

			$(".input-group-text").each(function() {
				$(this).removeClass("input-group-text").addClass("form-control");
			});

		} else {
			$(".input-group-text").each(function() {
				$(this).removeClass("input-group-text").addClass("form-control");
			});

			$(".form-control").each(function() {
				if (!$(this).hasClass("input-group-text")) {
					$(this).removeClass("form-control").addClass("input-group-text");
				}
			});
		}
	});


	$("input[type='text'], input[type='number']").on("input", function() {
		// Verificar si al menos uno de los inputs tiene valor
		let isAnyInputFilled = $("input[type='text']").toArray().some(input => $(input).val().trim() !== "");

		// Habilitar el botón si algún input tiene valor, deshabilitarlo si no
		$("#btnactcli").prop("disabled", !isAnyInputFilled);
	});

});




function actualizar() {
	
	Swal.fire({
		title: 'QUIERE ACTUALIZAR ESTOS DATOS?',
		text: '',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Sí, Quiero actualziar.',
		cancelButtonText: 'Cancelar'
	}).then((result) => {
		if (result.isConfirmed) {
			
			let clientDTO = {
			            nombres: $('#nombres').val(),
			            apellidos: $('#apellidos').val(),
						telefono: $('#telefono').val(),
			        };
			
			console.log(clientDTO);
			$('#loadingOverlay').show();
			$.ajax({
				url: '/users/cli/updatecli',  // Cambia la URL a la del endpoint al que deseas enviar la solicitud
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(clientDTO),	
			}).done(function(response) {
				Swal.fire(
					'Actualizado!',
					'Datos actualizados.',
					'success'
				);
				$("#btnactcli").prop("disabled", true);
			}).fail(function(xhr, status, error) {
				Swal.fire(
					'Error al actualizar!',
					'No se pudo actualizar los datos.',
					'error'
				);
			}).always(function() {
				$('#loadingOverlay').hide();
			});

		}
	});


}

