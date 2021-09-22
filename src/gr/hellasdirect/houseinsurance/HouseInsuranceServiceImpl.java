package gr.hellasdirect.houseinsurance;

import gr.hellasdirect.houseinsurance.chargecalculators.*;
import uk.co.floodwatch.FloodAreaCheckerTechnicalFailureException;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

import java.math.BigDecimal;

@SuppressWarnings("ClassCanBeRecord")
public class HouseInsuranceServiceImpl implements HouseInsuranceService {

    private final BaseAnnualPremiumChargeService baseAnnualPremiumService;
    private final ExtraChargesCalculatorRegistry calculatorRegistry;

    public HouseInsuranceServiceImpl(BaseAnnualPremiumChargeService baseAnnualPremiumService, ExtraChargesCalculatorRegistry calculatorRegistry) {
        this.baseAnnualPremiumService = baseAnnualPremiumService;
        this.calculatorRegistry = calculatorRegistry;
    }

    @Override
    public ServiceResponse<BigDecimal> getCalculatedPremium(HouseDetails house) {

        if (!this.validateInput(house)) {
            return new ServiceResponse<BigDecimal>()
                    .setCode(ServiceCode.INVALID_INPUT)
                    .setMessage("Invalid Input");
        }

        try {
            BigDecimal basePremium = this.baseAnnualPremiumService.get();
            float extraChargesPercentage = this.calculatorRegistry.calculate(house);
            BigDecimal result = this.addPercentageToBase(basePremium, extraChargesPercentage);

            return new ServiceResponse<BigDecimal>()
                    .setCode(ServiceCode.OK)
                    .setData(result);

        } catch (TooManyRoomsException exception) {
            return this.errorResponse(ServiceCode.TOO_MANY_ROOMS, exception.getMessage());
        } catch (ThatchedRoofException exception) {
            return this.errorResponse(ServiceCode.THATCHED_ROOF, exception.getMessage());
        } catch (PostcodeNotFoundException exception) {
            return this.errorResponse(ServiceCode.POST_CODE_NOT_FOUND, exception.getMessage());
        } catch (HighFloodRiskException exception) {
            return this.errorResponse(ServiceCode.FLOOD_RISK, exception.getMessage());
        } catch (SubsidenceAreaException exception) {
            return this.errorResponse(ServiceCode.SUBSIDENCE_AREA, exception.getMessage());
        } catch (SubsidenceAreaCheckerTechnicalFailureException | FloodAreaCheckerTechnicalFailureException exception) {
            return this.errorResponse(ServiceCode.THIRD_PARTY_SERVICE_ERROR, exception.getMessage());
        } catch (Exception exception) {
            return this.errorResponse(ServiceCode.GENERAL_ERROR, exception.getMessage());
        }
    }

    private ServiceResponse<BigDecimal> errorResponse(ServiceCode errorCode, String message) {
        return new ServiceResponse<BigDecimal>().setCode(errorCode).setMessage(message);
    }

    private boolean validateInput(HouseDetails house) {
        return house != null && house.getPostCode() != null;
    }

    private BigDecimal addPercentageToBase(BigDecimal basePremium, float percentage) {
        return basePremium.multiply(BigDecimal.valueOf(percentage + 1));
    }
}
