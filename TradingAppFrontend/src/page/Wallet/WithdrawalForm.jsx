import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { BanknoteIcon, HouseIcon } from "lucide-react";
import React from "react";

const WithdrawalForm = () => {
  const [amount, setAmount] = React.useState("");
  const handleChange = (e) => {
    const value = e.target.value;
    if (!isNaN(value) && value >= 0) {
      setAmount(value);
    }
  };
  const handleSubmit = () => {
    console.log("Amount: ", amount);
  };
  return (
    <div className="pt-10 space-y-5">
      <div className="flex justify-between items-center rounded-md bg-slate-900 text-xl font-bold px-5 py-4">
        <p>Available Balance</p>
        <p>$99006</p>
      </div>
      <div className="flex flex-col items-center">
        <h1>Enter Withdrawal Amount</h1>
        <div className="flex items-center justify-center">
          <Input
            onChange={handleChange}
            value={amount}
            placeholder="$999"
            type="number"
            className="withdrawalInput py-7 border-none outline-none focus:outline-none px-0 text-2xl text-center "
          />
        </div>
      </div>
      <div>
        <p className="pb-2">Transfer to </p>
        <div className="flex items-center gap-5 border px-5 py-2 rounded-md">
          <HouseIcon size={24} className="h-8 w-8" />
          <div>
            <p className="text-xl font-bold">YES BANK</p>
            <p className="text-xs">Account Number: ********890</p>
          </div>
        </div>
      </div>
      <DialogClose className="w-full">
        <Button className="w-full py-7 text-xl" onClick={handleSubmit}>
          Withdraw
        </Button>
      </DialogClose>
    </div>
  );
};

export default WithdrawalForm;
