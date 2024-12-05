import { Input } from "@/components/ui/input";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { DotFilledIcon } from "@radix-ui/react-icons";
import { Label } from "@/components/ui/label";
import React from "react";
import { Button } from "@/components/ui/button";

const TopUpForm = () => {
  const [amount, setAmount] = React.useState("");
  const handleChange = (e) => {
    const value = e.target.value;
    if (!isNaN(value) && value >= 0) {
      setAmount(value);
    }
  };
  const handleSubmit = () => {
    console.log("Amount: ", amount);
    console.log("Payment Method: ", paymentMethod);
  };
  const [paymentMethod, setPaymentMethod] = React.useState("RAZORPAY");
  const handlePaymentMethod = (value) => {
    setPaymentMethod(value);
  };

  return (
    <div className="pt-10 space-y-8">
      {/* Amount Input Section */}
      <div>
        <h1 className="pb-2 text-lg font-semibold">Enter Amount</h1>
        <Input
          onChange={handleChange}
          value={amount}
          className="py-4 text-lg"
          placeholder="$999"
          type="number"
        />
      </div>

      {/* Payment Method Section */}
      <div>
        <h1 className="pb-2 text-lg font-semibold">Select Payment Method</h1>
        <RadioGroup
          onValueChange={handlePaymentMethod}
          className="flex gap-6"
          defaultValue="RAZORPAY"
        >
          {/* Razorpay Option */}
          <div className="flex items-center space-x-4 border p-4 rounded-md shadow-md w-1/2">
            <RadioGroupItem
              icon={DotFilledIcon}
              className="h-6 w-6"
              value="RAZORPAY"
              id="razorpay"
            />
            <Label htmlFor="razorpay" className="text-lg font-medium">
              Razorpay
            </Label>
            <div className="bg-white rounded-md px-4 py-2 flex justify-center items-center">
              <img
                src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/razorpay-icon.png"
                alt="Razorpay logo"
                className="h-10 w-auto"
              />
            </div>
          </div>

          {/* Stripe Option */}
          <div className="flex items-center space-x-4 border p-4 rounded-md shadow-md w-1/2">
            <RadioGroupItem
              icon={DotFilledIcon}
              className="h-6 w-6"
              value="STRIPE"
              id="stripe"
            />
            <Label htmlFor="stripe" className="text-lg font-medium">
              Stripe
            </Label>
            <div className="bg-white rounded-md px-4 py-2 flex justify-center items-center">
              <img
                src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/stripe-icon.png"
                alt="Stripe logo"
                className="h-8 w-auto"
              />
            </div>
          </div>
        </RadioGroup>
      </div>
      <Button onClick={handleSubmit} className="w-full py-7">
        SUBMIT
      </Button>
    </div>
  );
};

export default TopUpForm;
