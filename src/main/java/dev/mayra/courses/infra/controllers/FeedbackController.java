package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.FeedbackService;
import dev.mayra.courses.entities.feedback.FeedbackCreateDTO;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.infra.controllers.docs.FeedbackControllerDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@Validated
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Operations related to the Feedback entity")
public class FeedbackController implements FeedbackControllerDoc {
  public final FeedbackService feedbackService;

  @GetMapping
  public ResponseEntity<List<FeedbackResponseDTO>> listAllFeedbacks() {
    return new ResponseEntity<>(feedbackService.listAllFeedbacks(), HttpStatus.OK);
  }

  @GetMapping("{courseCode}")
  public ResponseEntity<List<FeedbackResponseDTO>> listAllFeedbacksByCourseCode(@PathVariable String courseCode) {
    return new ResponseEntity<>(feedbackService.listAllFeedbacksByCourseCode(courseCode), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<FeedbackResponseDTO> createAFeedback(HttpServletRequest request, @RequestBody @Valid FeedbackCreateDTO feedback) throws Exception {
    return new ResponseEntity<>(feedbackService.create(request, feedback), HttpStatus.CREATED);
  }

}
