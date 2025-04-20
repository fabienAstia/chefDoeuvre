package com.personalities.services;

import com.personalities.dtos.MbtiTypeView;
import com.personalities.entities.MbtiType;
import com.personalities.entities.Profession;
import com.personalities.repositories.MbtiTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MbtiTypeService {

    private final MbtiTypeRepository mbtiTypeRepository;

    public MbtiTypeService(MbtiTypeRepository mbtiTypeRepository) {
        this.mbtiTypeRepository = mbtiTypeRepository;
    }

    public MbtiTypeView getMbtiType(String code) {
        MbtiType mbtiType = mbtiTypeRepository.findProjectedByCode(code);
        return new MbtiTypeView(
                mbtiType.getCode(),
                mbtiType.getName(),
                mbtiType.getDescription(),
                mbtiType.getPopulationPercentage(),
                mbtiType.getInterestingFact(),
                mbtiType.getImage(),
                mbtiType.getProfessions().stream().map(Profession::getName).toList(),
                buildStrengthAndWeaknesses(mbtiType)
        );
    }

    public Map<String, String> buildStrengthAndWeaknesses(MbtiType mbtiType) {
        Map<String, String> strengthsAndWeaknesses = new HashMap<>();
        mbtiType.getStrengthsAndWeaknesses().forEach(personalityTrait -> {
            strengthsAndWeaknesses.putIfAbsent(personalityTrait.getTrait(), personalityTrait.getEvaluation().getLabel());
        });
        return strengthsAndWeaknesses;
    }
}
