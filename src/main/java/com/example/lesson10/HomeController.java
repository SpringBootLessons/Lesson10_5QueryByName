package com.example.lesson10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

//  @Autowired tells the compiler to instantiate the repository objectwhen the
//  application runs, so you don’t have to type out that line so many times!
  @Autowired
  CourseRepository courseRepository;

  @RequestMapping("/")
  public String listCourses(Model model){

    Course a = new Course();
    a.setInstructor("Dave Wolf");
    a.setTitle("Java Programming");
    a.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin in orci auctor, interdum massa vitae, porttitor leo. Nunc pellentesque est id leo venenatis, vel efficitur dui molestie.");
    a.setCredit(3);
    courseRepository.save(a);
    Course b = new Course();
    b.setInstructor("Dave Wolf");
    b.setTitle("Python Automation");
    b.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin in orci auctor, interdum massa vitae, porttitor leo. Nunc pellentesque est id leo venenatis, vel efficitur dui molestie.");
    b.setCredit(5);
    courseRepository.save(b);
    Course c = new Course();
    c.setInstructor("Alton Henley");
    c.setTitle("Python Fundamentals");
    c.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin in orci auctor, interdum massa vitae, porttitor leo. Nunc pellentesque est id leo venenatis, vel efficitur dui molestie.");
    c.setCredit(4);
    courseRepository.save(c);

    model.addAttribute("courses", courseRepository.findAll());
    return "list";
  }

  @GetMapping("/add")
  public String courseForm(Model model){
    model.addAttribute("course", new Course());
    return "courseform";
  }

  @PostMapping("/process")
  public String processForm(@Valid Course course, BindingResult result){
    if (result.hasErrors()){
      return "courseform";
    }
    courseRepository.save(course);
    return "redirect:/";
  }
    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }

    // Search Methods
    @RequestMapping("/search")
    public String search(){
        return "searchform";
    }

    @PostMapping("/findbytitle")
    public String findbytitle(@RequestParam("title") String title, Model model){
        model.addAttribute("course", courseRepository.findByTitle(title));
        return "show";
    }

    @PostMapping("/findbytitlecontaining")
    public String findbytitlecontaining(@RequestParam("partialtitle") String partialtitle, Model model){
        model.addAttribute("courses", courseRepository.findAllByTitleContainingIgnoreCase(partialtitle));
        return "list";
    }

    @PostMapping("/findbycredits")
    public String findbycredits(@RequestParam("credits") int credits, Model model){
        model.addAttribute("courses", courseRepository.findAllByCredit(credits));
        return "list";
    }

    @PostMapping("/findbycreditsbetween")
    public String findbycreditsbetween(@RequestParam("credits_min") int min,@RequestParam("credits_max") int max, Model model){
        model.addAttribute("courses", courseRepository.findAllByCreditBetween(min,max));
        return "list";
    }

    @PostMapping("/countbyinstructor")
    public String countbyinstructor(@RequestParam("instructor") String instructor, Model model){
        model.addAttribute("instructor",instructor);
        model.addAttribute("counter", courseRepository.countAllByInstructor(instructor));
        return "results";
    }


}