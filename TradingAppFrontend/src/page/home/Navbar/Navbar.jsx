import React from 'react';
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import { DragHandleHorizontalIcon, MagnifyingGlassIcon } from '@radix-ui/react-icons';
import { Avatar } from "@/components/ui/avatar";
import { AvatarFallback, AvatarImage } from 'radix-ui';
import Sidebar from './Sidebar';

const Navbar = () => {
  return (
<div className="w-full px-0 py-0 border-b bg-background sticky top-0 inset-x-0 z-50 flex justify-between items-center">
{/* Left Section */}
      <div className="flex items-center gap-3">
        <Sheet>
          <SheetTrigger>
            <Button variant="ghost" size="icon" className="rounded-full h-11 w-11">
              <DragHandleHorizontalIcon className="w-7 h-7" />
            </Button>
          </SheetTrigger>
          <SheetContent side="left" className="w-72 border-r-0 flex flex-col justify-center">
            <SheetHeader>
              <SheetTitle>
                <div className='text-3xl flex justify-center items-center gap-1'>
                  <Avatar>
                    <AvatarImage src='https://cdn.pixabay.com/photo/2020/09/07/10/44/moneybag-5551484_1280.png'/>
                  </Avatar>
                  <div>
                  <span className='font-bold text-blue-400'>Nikunj</span>
                  <span>Trading</span>
</div>
</div>
              </SheetTitle>
              
    
            </SheetHeader>
            <Sidebar/>
          </SheetContent>
        </Sheet>
        <p className='text-sm lg:text-base cursor-pointer'>
          Nikunj Trading
        </p>
        <div className='p-0 ml-9'>
          <Button variant="outline" className='flex items-center gap-3'>
            <MagnifyingGlassIcon/>
            <span>
              Search
            </span>
          </Button>
        </div>
      </div>
      <div className='items-center'>
        <Avatar>
          <AvatarFallback>
            N
          </AvatarFallback>
        </Avatar>
      </div>
    </div>
  );
};

export default Navbar;
