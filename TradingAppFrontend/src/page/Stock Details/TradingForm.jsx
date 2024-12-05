import { Input } from "@/components/ui/input";
import React from "react";

const TradingForm = () => {
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
          />
          <div>
            <p></p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TradingForm;
