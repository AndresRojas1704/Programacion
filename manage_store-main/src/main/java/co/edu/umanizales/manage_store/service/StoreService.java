package co.edu.umanizales.manage_store.service;

import co.edu.umanizales.manage_store.controller.dto.StoreDto;
import co.edu.umanizales.manage_store.model.Store;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class StoreService {
    private final List<Store> stores;

    @Autowired
    private SaleService saleService;
    public StoreService() {
        stores = new ArrayList<>();

    }

    public void addStore(Store store) {

        stores.add(store);
    }

    public Store getStoreById(String code) {
        for (Store store : stores) {
            if (store.getCode().equalsIgnoreCase(code)) {
                return store;
            }
        }
        return null;
    }

    public List<StoreDto>ventasXlacantidad(int cant){
        List<StoreDto> storesDto = new ArrayList<>();
        for (Store store : stores){
            StoreDto storeDto = new StoreDto(new Store(store.getCode(), store.getName()), saleService.getTotalSalesByStore(store.getCode()));
            if (storeDto.getCant() >cant){
                storesDto.add(storeDto);
            }
        }
        return storesDto;
    }

}
