package com.rafaeldebiase.taskmanager.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rafaeldebiase.taskmanager.domain.enums.StatusTarefa;
import com.rafaeldebiase.taskmanager.dto.TarefaDto;
import com.rafaeldebiase.taskmanager.dto.TarefaNewDto;

public class Useful {
	
	public static Calendar convertsStringForCalendar(String data) throws ParseException {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calendario = Calendar.getInstance();
		
		calendario.setTime(formatoData.parse(data));
		
		return calendario; 
		
	}

	public static String convertsCalendarForString(Calendar data) throws ParseException {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataConvertida = formatoData.format(data.getTime());
		
		return dataConvertida;
		
	}

	
	public static StatusTarefa verrifyStatusTarefa(TarefaNewDto objDto) throws ParseException {
		
		Calendar newCalendar = Useful.convertsStringForCalendar(objDto.getDataPrevisaoEntrega());
		
		if (newCalendar.before(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.ATRASADA;
		} else if (newCalendar.equals(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (newCalendar.after(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		}
		return StatusTarefa.CONCLUIDO;
	}
	
public static StatusTarefa verrifyStatusTarefa(TarefaDto objDto) throws ParseException {
		
		Calendar newCalendar = Useful.convertsStringForCalendar(objDto.getDataPrevisaoConclusao());
		
		if (newCalendar.before(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.ATRASADA;
		} else if (newCalendar.equals(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		} else if (newCalendar.after(Calendar.getInstance()) && !objDto.getConcluido()) {
			return StatusTarefa.PENDENTE;
		}
		return StatusTarefa.CONCLUIDO;
	}
}
