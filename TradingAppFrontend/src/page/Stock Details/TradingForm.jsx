import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { DotIcon } from "lucide-react";
import React, { useState } from "react";

const TradingForm = () => {
  const [amount, setAmount] = useState("");
  const [orderType, setOrderType] = useState("Buy");
  const handleChange = (e) => {
    setAmount(e.target.value);
    console.log(e.target.value);
  };

  return (
    <div className="space-y-10 p-5">
      <div>
        <div className="flex gap-4 items-center justify-between">
          <Input
            className="py-7 focus:outline-none"
            placeholder="Enter Amount"
            onChange={handleChange}
            name="amount"
            type="number"
            value={amount}
          />
          <div>
            <p className="border text-2xl flex justify-center items-center w-36 h-14 rounded-md">
              4563
            </p>
          </div>
        </div>
        {false && (
          <h1 className="text-red-600 text-center pt-4">
            Insufficient wallet balance to buy
          </h1>
        )}
      </div>

      <div>
        <div className="flex items-center gap-2">
          <p>BTC</p>
          <DotIcon className="text-gray-400" />
          <p className="text-gray-400">Bitcoin</p>
        </div>
        <div className="flex items-end gap-2">
          <p className="text-xl font-bold">$6553</p>
          <p className="text-red-600">
            <span>-131314151341.578</span>
            <span>(-0.29803%)</span>
          </p>
        </div>
      </div>

      <div className="flex items-center justify-between">
        <p>Order Type</p>
        <p>Market Order</p>
      </div>

      <div className="flex items-center justify-between">
        <p>{orderType == "BUY" ? "Available Cash" : "Available Quantity"}</p>
        <p>{orderType == "BUY" ? 90000 : 14561}</p>
      </div>
      <div>
        <Button
          className={`w-full py-6 ${
            orderType == "SELL" ? "bg-red-600" : "bg-green-600"
          }`}
        >
          {orderType}
        </Button>
        <Button
          variant="link"
          className="w-full mt-5 text-xl"
          onClick={() => setOrderType(orderType == "BUY" ? "SELL" : "BUY")}
        >
          OR {orderType == "BUY" ? "SELL" : "BUY"}
        </Button>
      </div>
    </div>
  );
};

export default TradingForm;
