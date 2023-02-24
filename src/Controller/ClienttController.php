<?php

namespace App\Controller;
use App\Entity\Clientt;
use App\Form\ClienttType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\ClienttRepository;
use Doctrine\Persistence\ManagerRegistry;

class ClienttController extends AbstractController
{
    #[Route('/', name: 'app_clientt')]
    public function index(): Response
    {  
        $clients = $this->getDoctrine()->getRepository(Clientt::class)->findAll();
        return $this->render('clientt/index.html.twig', ['clients' => $clients]);
       
    }


    /**
     * @Route("/admin", name="display_admin")
     */
    public function indexAdmin(): Response
    {

        return $this->render('Admin/index.html.twig'
        );
    }

     /**
     * @Route("/addClientt", name="addClient")
     */
    public function addClientt(Request $request): Response
    {
        $clientt = new Clientt();

        $form = $this->createForm(ClienttType::class,$clientt);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($clientt);//Add
            $em->flush();

            return $this->redirectToRoute('app_clientt');
        }
        return $this->render('clientt/createClientt.html.twig',['f'=>$form->createView()]);




    }
    
    public function suppC(Clientt  $clientt): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($clientt);
        $em->flush();

        return $this->redirectToRoute('app_clientt');


    }
     /**
     * @Route("/modifClientt/{id}", name="modifClientt" ))
     */
    public function modifClientt(Request $request,$id): Response
    {
        $clientt = $this->getDoctrine()->getManager()->getRepository(Clientt::class)->find($id);

        $form = $this->createForm(ClienttType::class,$clientt);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('app_clientt');
        }
        return $this->render('clientt/updateClientt.html.twig',[
            
            'f'=>$form->createView()]);




    }
   
    /*public function modifClientt(ManagerRegistry $doctrine,
    Request $request,$id,ClienttRepository $clientt){
 
    $clientt=$clientt->find($id);
  $form=$this->createForm(ClienttFormType::class,
  $classroom);
  $form->handleRequest($request);
  
        if($form->isSubmitted()){
            $em =$doctrine->getManager() ;
            $em->flush();
            return $this->redirectToRoute("app_clientt");}
        return $this->renderForm("clientt/updateClientt.html.twig",
            array("f"=>$form));
     }*/
    
      /**
     * @Route("/client", name="display_client")
     */
    public function indexClient(): Response
    {

        return $this->render('Clients/index.html.twig');
    }
}
