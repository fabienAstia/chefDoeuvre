package co.simplon.personalities.dtos;

public record OfferJobView(
        String title,
        String contractType,
        String experience,
        String description,
        String companyName,
        CoordinatesView coordinates,
        String workingHours,
        String salary,
        String sourceUrl
) {
}
