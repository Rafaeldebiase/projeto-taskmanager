package com.rafaeldebiase.taskmanager.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.rafaeldebiase.taskmanager.domain.Usuario;
import com.rafaeldebiase.taskmanager.dto.UsuarioDto;
import com.rafaeldebiase.taskmanager.repository.UsuarioRepository;
import com.rafaeldebiase.taskmanager.resource.exception.FieldMessage;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioDto> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UsuarioRepository repository;

	@Override
	public void initialize(UsuarioUpdate ann) {
	}

	@Override
	public boolean isValid(UsuarioDto objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Usuario aux = repository.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
