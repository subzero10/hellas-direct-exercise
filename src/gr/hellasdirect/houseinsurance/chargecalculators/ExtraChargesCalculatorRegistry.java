package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtraChargesCalculatorRegistry {

    private final List<PremiumExtraChargeCalculator> calculatorList;

    public ExtraChargesCalculatorRegistry() {
        this.calculatorList = new ArrayList<>();
    }

    public void registerCalculator(PremiumExtraChargeCalculator calculator) {
        this.calculatorList.add(calculator);
    }

    public void registerCalculators(PremiumExtraChargeCalculator[] calculators) {
        this.calculatorList.addAll(Arrays.asList(calculators));
    }

    public float calculate(HouseDetails house) throws Exception {
        float result = 0;
        for (PremiumExtraChargeCalculator premiumExtraChargeCalculator : calculatorList) {
            result += premiumExtraChargeCalculator.calculateCharge(house);
        }
        return result;
    }
}
