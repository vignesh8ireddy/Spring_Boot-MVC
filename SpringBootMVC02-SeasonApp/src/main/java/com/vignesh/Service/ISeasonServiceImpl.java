package com.vignesh.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class ISeasonServiceImpl implements ISeasonService {

	int month = LocalDate.now().getMonthValue();
	
	@Override
	public String getSeason() {
		
		if(month<=2 || month==12) return "Winter";
		else if(month<6) return "Spring";
		else if(month<9) return "Summer";
		else return "Fall";
		
	}

}
