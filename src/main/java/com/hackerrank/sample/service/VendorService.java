package com.hackerrank.sample.service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> getVendor() {
        return vendorRepository.findAll();
    }

    public Vendor getVendor(Long itemId) {
        return vendorRepository.findById(itemId).orElseThrow(()-> new NoSuchResourceFoundException("Vendor with given id " + itemId + " not found."));
    }

    public Vendor addVendor(Vendor vendor) {

        try{
            if (getVendor(vendor.getVendorId()) == null){
                return vendorRepository.save(vendor);
            }else {
                throw new BadResourceRequestException("Vendor with same id:" + vendor.getVendorId() + " exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public Vendor updateVendor(Long itemId,Vendor vendor) {

        try{
            if (getVendor(itemId) != null){
                return vendorRepository.save(vendor);
            }else {
                throw new BadResourceRequestException("Vendor with id:" + vendor.getVendorId() + " doesn't exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public void deleteItem(Long itemId) {
        if (getVendor(itemId) != null){
            vendorRepository.deleteById(itemId);
        }else {
            throw new NoSuchResourceFoundException("Vendor with given id " + itemId + " not found.");
        }
    }

    public void deleteItems() {
        vendorRepository.deleteAllInBatch();
    }
}
