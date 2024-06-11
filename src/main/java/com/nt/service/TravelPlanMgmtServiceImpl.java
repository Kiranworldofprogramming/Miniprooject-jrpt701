package com.nt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.PlanCategory;
import com.nt.entity.TravelPlan;
import com.nt.repository.IPlanCategoryRepository;
import com.nt.repository.ITravelPlanRepository;

@Service
public class TravelPlanMgmtServiceImpl implements ITravelPlanMgmtService {

	@Autowired
	private ITravelPlanRepository travelPlanRepo;
	@Autowired
	private IPlanCategoryRepository planCategoryRepo;
	
	
	@Override
	public String registerTravelPlan(TravelPlan plan) {
		TravelPlan saved = travelPlanRepo.save(plan);
		return saved.getPlanId()!=null?"Travel plan is saved with id value "+saved.getPlanId():"Problem in saving TourPlan";
	}

	@Override
	public Map<Integer, String> getTravelPlanCategories() {
		List<PlanCategory> list = planCategoryRepo.findAll();
		Map<Integer,String> categoriesMap=new HashMap<Integer,String>();
		list.forEach(category -> {
			categoriesMap.put(category.getCategoryId(), category.getCategoryName());
		});
		return categoriesMap;
	}

	@Override
	public List<TravelPlan> showAllTravelPlans() {
		return travelPlanRepo.findAll();
	}

	@Override
	public TravelPlan showTravelPlanById(Integer planId) {
		return travelPlanRepo.findById(planId).orElseThrow(()-> new IllegalArgumentException("Travel plan not found"));
	}

	@Override
	public String updateTravelPlan(TravelPlan plan) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(plan.getPlanId());
		if(opt.isEmpty()) {
			travelPlanRepo.save(plan);
			return plan.getPlanId()+" travel plan is updated";
		}else {
			return plan.getPlanId()+" travel plan not found";
		}
		 
	}

	@Override
	public String deleteTravelPlan(Integer planId) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			travelPlanRepo.deleteById(planId);
			return planId+" Travel plan is deleted";
		}else {
			return planId+" Travel plan not found";
		}
		
	}

	@Override
	public String changeTravelPlanStatus(Integer planId, String status) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			TravelPlan plan=opt.get();
			plan.setActivateSw(status);
			travelPlanRepo.save(plan);
			return planId+" Travel plan status is changed";
		}else {
			return planId+" Travel plan not found for updatation";
		}
	}

}
