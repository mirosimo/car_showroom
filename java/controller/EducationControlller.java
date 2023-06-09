package mirosimo.car_showroom2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mirosimo.car_showroom2.model.Education;
import mirosimo.car_showroom2.service.EducationService;

@Controller
public class EducationControlller {
	@Autowired
	EducationService educationService;
	
	@GetMapping("/education-list")
	public String listEducationView(Model model) {
		model.addAttribute("listEducation", educationService.getAllEntities());
		return "education-list";
	}
	
	@GetMapping("/education-new")
	public String newEducationView(Model model) {
		Education education = new Education();
		model.addAttribute("education", education);
		return "education-new";
	} 
	
	/*@PostMapping("/education-save")
	public String saveEntity(@ModelAttribute("education") Education education) {
		
		this.educationService.saveEntity(education);
		return "redirect:/education-list";
	}*/
	
	@PostMapping("/education-save")
	public String saveEntity(@Valid Education education, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("education", education);
			return "education-new";
		} 
		this.educationService.saveEntity(education);
		return "redirect:/education-list";
	}
			
	@GetMapping("/education-delete/{id}")
	public String deleteEntity(@PathVariable (value="id") long id) {
		this.educationService.deleteEntityById(id);
		return "redirect:/education-list";
	}
}
