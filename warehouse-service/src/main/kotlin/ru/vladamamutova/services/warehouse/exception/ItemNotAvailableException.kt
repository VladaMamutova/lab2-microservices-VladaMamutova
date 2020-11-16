package ru.vladamamutova.services.warehouse.exception

class ItemNotAvailableException(model: String?, size: String?) :
        RuntimeException("Item with model $model and size $size is out of stock")
